package br.ufmg.dcc.saracura.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class Pessoa {
    private final String cpf;
    private final String nome;
    private final LocalDate dataNascimento;
}
