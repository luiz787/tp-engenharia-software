package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.Paciente;
import br.ufmg.dcc.saracura.exception.BusinessException;
import br.ufmg.dcc.saracura.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public Paciente cadastrarPaciente(final Paciente paciente) {
        return pacienteRepository.cadastrarPaciente(paciente);
    }

    public Paciente consultarPacientePorCpf(final String cpf) {
        final var paciente = pacienteRepository.consultarPacientePorCpf(cpf);
        if (paciente == null) {
            throw new BusinessException("Paciente com CPF " + cpf + " não está cadastrado na clínica.");
        }
        return paciente;
    }
}
