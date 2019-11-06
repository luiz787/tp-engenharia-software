package br.ufmg.dcc.saracura.domain;

import br.ufmg.dcc.saracura.dto.RequisicaoConsulta;
import br.ufmg.dcc.saracura.dto.RequisicaoExame;
import br.ufmg.dcc.saracura.exception.BusinessException;
import br.ufmg.dcc.saracura.repository.ConsultaRepository;
import br.ufmg.dcc.saracura.repository.ExameRepository;
import br.ufmg.dcc.saracura.service.EquipamentoService;
import br.ufmg.dcc.saracura.service.MedicoService;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.context.TestPropertySource;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class PacienteTest {
    @Mock
    private MedicoService medicoService;
    @Mock
    private ConsultaRepository consultaRepository;
    @Mock
    private EquipamentoService equipamentoService;
    @Mock
    private ExameRepository exameRepository;

    @InjectMocks
    public Paciente paciente = construirPaciente();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private Paciente construirPaciente() {
        final String nome = "Teste SaraCura";
        final String cpf = "12346611233";
        final LocalDate dataNascimento = LocalDate.of(1950, 1, 1);
        final String telefone = "(11) 97070-7070";
        return new Paciente(cpf, nome, dataNascimento, telefone, medicoService, consultaRepository, equipamentoService,
                exameRepository);
    }

    @Test
    public void testNewPaciente_Sucesso() {
        final var paciente = construirPaciente();
        Assertions.assertNotNull(paciente);
    }

    @Test
    public void testNewPaciente_TelefoneNulo_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Paciente("78944132555", "SemTelefone", LocalDate.of(1999, 1, 1),
                    null, medicoService, consultaRepository, equipamentoService,
                    exameRepository);
        });
    }

    @Test
    public void testSolicitarConsulta_NenhumMedicoDisponivel_BusinessException() {
        final var pacienteSolicitante = construirPaciente();
        final var horaInicial = LocalDateTime.of(2020, 1, 1, 15, 0);
        final var horaFinal = LocalDateTime.of(2020, 1, 1, 16, 0);
        final RequisicaoConsulta requisicaoConsulta = new RequisicaoConsulta(pacienteSolicitante.getCpf(),
                horaInicial, horaFinal, Especialidade.OFTALMOLOGIA);

        when(medicoService.obterMedicosDisponiveisPeriodo(horaInicial, horaFinal, Especialidade.OFTALMOLOGIA)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(BusinessException.class, () -> {
            pacienteSolicitante.solicitarConsulta(requisicaoConsulta);
        }, "Não há médicos disponíveis para a especialidade "
                + Especialidade.OFTALMOLOGIA + " no horário requisitado (" + horaInicial + " - " + horaFinal + ").");
    }

    @Test
    public void testSolicitarConsulta_Sucesso() {
        final var pacienteSolicitante = construirPaciente();
        final var horaInicial = LocalDateTime.of(2020, 1, 1, 15, 0);
        final var horaFinal = LocalDateTime.of(2020, 1, 1, 16, 0);
        final RequisicaoConsulta requisicaoConsulta = new RequisicaoConsulta(pacienteSolicitante.getCpf(),
                horaInicial, horaFinal, Especialidade.OFTALMOLOGIA);

        when(medicoService.obterMedicosDisponiveisPeriodo(horaInicial, horaFinal, Especialidade.OFTALMOLOGIA))
                .thenReturn(construirMockMedico());
        when(consultaRepository.persistirConsulta(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());

        final var consulta = pacienteSolicitante.solicitarConsulta(requisicaoConsulta);

        Assertions.assertEquals(pacienteSolicitante, consulta.getPaciente());
    }

    private List<Medico> construirMockMedico() {
        final var medicos = new ArrayList<Medico>();
        final var medico = new Medico("47215589811", "Alberto",
                LocalDate.of(1977, 5, 24),
                1000000, "74451", Especialidade.OFTALMOLOGIA);
        medicos.add(medico);
        return medicos;
    }

    @Test
    public void testSolicitarExame_NenhumEquipamentoDisponivel_BusinessException() {
        final var pacienteSolicitante = construirPaciente();
        final var horaInicial = LocalDateTime.of(2020, 1, 1, 15, 0);
        final var requisicaoExame = new RequisicaoExame(pacienteSolicitante.getCpf(), TipoExame.IMAGEM, horaInicial);

        when(equipamentoService.obterEquipamentos()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(BusinessException.class, () -> pacienteSolicitante.solicitarExame(requisicaoExame),
                "Não foi possível marcar o exame pois não há nenhum equipamento do tipo "
                        + TipoEquipamento.EQUIPAMENTO_ULTRASSOM + " disponível.");
    }

    @Test
    public void testSolicitarExame_Sucesso() {
        final var pacienteSolicitante = construirPaciente();
        final var horaInicial = LocalDateTime.of(2020, 1, 1, 15, 0);
        final var requisicaoExame = new RequisicaoExame(pacienteSolicitante.getCpf(), TipoExame.IMAGEM, horaInicial);

        when(equipamentoService.obterEquipamentos()).thenReturn(construirMockEquipamento());
        when(exameRepository.cadastrarExame(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());

        final var exame = pacienteSolicitante.solicitarExame(requisicaoExame);

        Assertions.assertEquals(pacienteSolicitante, exame.getPaciente());
        Assertions.assertEquals(Pair.with(horaInicial, horaInicial.plusMinutes(TipoExame.IMAGEM.getDuracao().toMinutes())),
                exame.getHorarios());
    }

    private List<Equipamento> construirMockEquipamento() {
        final List<Equipamento> equipamentos = new ArrayList<>();
        final Equipamento equipamento = new Equipamento("JWFEIO", "Máquina de Ultrassom 1",
                "Máquina utilizada para exames de imagem.", TipoEquipamento.EQUIPAMENTO_ULTRASSOM);
        equipamentos.add(equipamento);
        return equipamentos;
    }
}
