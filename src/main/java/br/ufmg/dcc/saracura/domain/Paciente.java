package br.ufmg.dcc.saracura.domain;

import br.ufmg.dcc.saracura.dto.RequisicaoConsulta;
import br.ufmg.dcc.saracura.dto.RequisicaoExame;
import br.ufmg.dcc.saracura.exception.BusinessException;
import br.ufmg.dcc.saracura.repository.ConsultaRepository;
import br.ufmg.dcc.saracura.repository.ExameRepository;
import br.ufmg.dcc.saracura.service.EquipamentoService;
import br.ufmg.dcc.saracura.service.MedicoService;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Paciente extends Pessoa {

    private final MedicoService medicoService;
    private final ConsultaRepository consultaRepository;
    private final EquipamentoService equipamentoService;
    private final ExameRepository exameRepository;
    private String telefone;

    public Paciente(final String cpf, final String nome, final LocalDate dataNascimento, final String telefone,
                    final MedicoService medicoService, final ConsultaRepository consultaRepository,
                    final EquipamentoService equipamentoService, final ExameRepository exameRepository) {
        super(cpf, nome, dataNascimento);
        this.telefone = Objects.requireNonNull(telefone, "Telefone não pode ser nulo.");
        this.medicoService = medicoService;
        this.consultaRepository = consultaRepository;
        this.equipamentoService = equipamentoService;
        this.exameRepository = exameRepository;
    }

    public Consulta solicitarConsulta(final RequisicaoConsulta requisicaoConsulta) {
        final var especialidadeRequerida = requisicaoConsulta.getEspecialidade();
        final Medico medico = obterAlgumMedicoDisponivel(requisicaoConsulta.getHoraInicial(),
                requisicaoConsulta.getHoraFinal(), especialidadeRequerida);
        final Consulta consulta = new Consulta(requisicaoConsulta.getHoraInicial(), requisicaoConsulta.getHoraFinal(), medico,
                this);
        return consultaRepository.persistirConsulta(consulta);
    }

    private Medico obterAlgumMedicoDisponivel(final LocalDateTime horaInicial, final LocalDateTime horaFinal,
                                              final Especialidade especialidadeRequerida) {
        var medicosDisponiveisPeriodo = medicoService.obterMedicosDisponiveisPeriodo(horaInicial,
                horaFinal, especialidadeRequerida);
        return medicosDisponiveisPeriodo
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Não há médicos disponíveis para a especialidade "
                        + especialidadeRequerida + " no horário requisitado (" + horaInicial + " - " + horaFinal + ")."));
    }

    public Exame solicitarExame(final RequisicaoExame requisicaoExame) {
        final var equipamentos = equipamentoService.obterEquipamentos();
        final var tiposEquipamentosNecessarios = requisicaoExame.getTipoExame().getTiposEquipamentosNecessarios();
        final var equipamentosPorTipo = obterEquipamentosTiposRelevantesAgrupadosPorTipo(equipamentos, tiposEquipamentosNecessarios);
        final var horarioFinal = obterHorarioFinalExame(requisicaoExame);
        final var equipamentosReservados = reservarEquipamentosDisponiveis(requisicaoExame, tiposEquipamentosNecessarios,
                equipamentosPorTipo, horarioFinal);

        final var exame = new Exame(this, requisicaoExame.getHorarioInicial(), horarioFinal, equipamentosReservados);
        return exameRepository.cadastrarExame(exame);
    }

    private List<Equipamento> reservarEquipamentosDisponiveis(final RequisicaoExame requisicaoExame,
                                                                   final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios,
                                                                   final Map<TipoEquipamento, List<Equipamento>> equipamentosPorTipo,
                                                                   final LocalDateTime horarioFinal) {
        final var equipamentosReservados = new ArrayList<Equipamento>(tiposEquipamentosNecessarios.size());
        for (var tipoEquipamento : tiposEquipamentosNecessarios) {
            final var equipamento = obterAlgumEquipamentoDisponivel(requisicaoExame, horarioFinal, tipoEquipamento,
                    equipamentosPorTipo.get(tipoEquipamento));
            equipamentosReservados.add(equipamento);
        }
        return equipamentosReservados;
    }

    private LocalDateTime obterHorarioFinalExame(RequisicaoExame requisicaoExame) {
        return requisicaoExame
                .getHorarioInicial()
                .plusMinutes(requisicaoExame.getTipoExame().getDuracao().toMinutes());
    }

    private Map<TipoEquipamento, List<Equipamento>> obterEquipamentosTiposRelevantesAgrupadosPorTipo(
            final List<Equipamento> equipamentos, final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios) {
        final var equipamentosTiposRelevantes = equipamentos
                .stream()
                .filter(equipamento -> tiposEquipamentosNecessarios.contains(equipamento.getTipo()))
                .collect(Collectors.toList());
        return equipamentosTiposRelevantes
                .stream()
                .collect(Collectors.groupingBy(Equipamento::getTipo));
    }

    private Equipamento obterAlgumEquipamentoDisponivel(final RequisicaoExame requisicaoExame,
                                                        final LocalDateTime horarioFinal,
                                                        final TipoEquipamento tipoEquipamento,
                                                        final List<Equipamento> equipamentos) {
        if (equipamentos == null) {
            throw new BusinessException("Não foi possível marcar o exame pois não há nenhum equipamento do tipo "
                    + tipoEquipamento + " disponível.");
        }

        return equipamentos
                .stream()
                .filter(e -> e.verificarDisponibilidade(requisicaoExame.getHorarioInicial(), horarioFinal))
                .findFirst()
                .orElseThrow(()-> new BusinessException("Não foi possível marcar o exame pois não há nenhum equipamento do tipo "
                        + tipoEquipamento + " disponível."));
    }
}
