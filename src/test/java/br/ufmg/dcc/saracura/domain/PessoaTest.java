package br.ufmg.dcc.saracura.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PessoaTest {

    @Test
    public void testNewPessoa_CpfNulo_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Pessoa(null, "Teste", LocalDate.of(1990,1,1)),
                "CPF não pode ser nulo.");
    }

    @Test
    public void testNewPessoa_NomeNulo_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Pessoa("44922874155", null, LocalDate.of(1990,1,1)),
                "Nome não pode ser nulo.");
    }

    @Test
    public void testNewPessoa_DataNascimentoNula_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Pessoa("44922874155", "Teste", null),
                "Data de nascimento não pode ser nula.");
    }
}
