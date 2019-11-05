package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.Consulta;
import br.ufmg.dcc.saracura.domain.Especialidade;
import br.ufmg.dcc.saracura.domain.Medico;
import br.ufmg.dcc.saracura.domain.Paciente;
import br.ufmg.dcc.saracura.dto.RequisicaoConsulta;
import br.ufmg.dcc.saracura.exception.BusinessException;
import br.ufmg.dcc.saracura.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final PacienteService pacienteService;

    private final ConsultaRepository consultaRepository;

    private final MedicoService medicoService;

    public Consulta marcarConsulta(final RequisicaoConsulta requisicaoConsulta) {
        final var especialidadeRequerida = requisicaoConsulta.getEspecialidade();
        final Medico medico = obterAlgumMedicoDisponivel(requisicaoConsulta.getHoraInicial(),
                requisicaoConsulta.getHoraFinal(), especialidadeRequerida);

        final Paciente paciente = pacienteService.consultarPacientePorCpf(requisicaoConsulta.getCpf());
        Consulta consulta = new Consulta(requisicaoConsulta.getHoraInicial(), requisicaoConsulta.getHoraFinal(), medico,
                paciente);
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

}
