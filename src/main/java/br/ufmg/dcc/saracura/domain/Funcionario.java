package br.ufmg.dcc.saracura.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Funcionario extends Pessoa {

    private long salarioCentavos;

    public Funcionario(final String cpf, final String nome, final LocalDate dataNascimento,
                       final long salarioInicialCentavos) {
        super(cpf, nome, dataNascimento);
        this.salarioCentavos = salarioInicialCentavos;
    }

    public long getSalarioCentavos() {
        return salarioCentavos;
    }

}
