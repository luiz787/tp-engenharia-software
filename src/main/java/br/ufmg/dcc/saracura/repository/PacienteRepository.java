package br.ufmg.dcc.saracura.repository;

import br.ufmg.dcc.saracura.domain.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository {
    Paciente cadastrarPaciente(Paciente paciente);

    Paciente consultarPacientePorCpf(String cpf);
}
