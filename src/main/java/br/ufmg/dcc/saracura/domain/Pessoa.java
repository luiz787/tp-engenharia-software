package br.ufmg.dcc.saracura.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Data
@EqualsAndHashCode
@ToString
public class Pessoa {
    private final String cpf;
    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(final String cpf, final String nome, final LocalDate dataNascimento) {
        this.cpf = Objects.requireNonNull(cpf, "CPF não pode ser nulo.");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento não pode ser nula.");
    }
}
