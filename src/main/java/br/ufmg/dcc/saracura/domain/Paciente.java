package br.ufmg.dcc.saracura.domain;

import java.time.LocalDate;

public class Paciente extends Pessoa {

    private String telefone;

    public Paciente(final String cpf, final String nome, final LocalDate dataNascimento, final String telefone) {
        super(cpf, nome, dataNascimento);
        this.telefone = telefone;
    }

    public boolean solicitarConsulta() {
        // TODO: implementar
        return false;
    }

    public boolean solicitarExame() {
        // TODO: implementar
        return false;
    }


}
