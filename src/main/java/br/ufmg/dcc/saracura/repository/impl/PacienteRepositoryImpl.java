package br.ufmg.dcc.saracura.repository.impl;

import br.ufmg.dcc.saracura.domain.Paciente;
import br.ufmg.dcc.saracura.repository.PacienteRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PacienteRepositoryImpl implements PacienteRepository {

    private final Map<String, Paciente> pacientes;

    public PacienteRepositoryImpl() {
        pacientes = new ConcurrentHashMap<>();
    }

    @Override
    public Paciente cadastrarPaciente(Paciente paciente) {
        final var cpf = paciente.getCpf();
        pacientes.put(cpf, paciente);
        return pacientes.get(cpf);
    }

    @Override
    public Paciente consultarPacientePorCpf(String cpf) {
        return pacientes.get(cpf);
    }

}
