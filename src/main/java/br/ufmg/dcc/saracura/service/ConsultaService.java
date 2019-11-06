package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.Consulta;
import br.ufmg.dcc.saracura.domain.Paciente;
import br.ufmg.dcc.saracura.dto.RequisicaoConsulta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final PacienteService pacienteService;

    public Consulta marcarConsulta(final RequisicaoConsulta requisicaoConsulta) {
        final Paciente paciente = pacienteService.consultarPacientePorCpf(requisicaoConsulta.getCpf());
        return paciente.solicitarConsulta(requisicaoConsulta);
    }

}
