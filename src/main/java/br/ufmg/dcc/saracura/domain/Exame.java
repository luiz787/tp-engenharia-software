package br.ufmg.dcc.saracura.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Exame extends Evento {

    private final String codigo;
    private final String nome;
    private final String descricao;
    private final List<Equipamento> equipamentosNecessarios;

    public Exame(final LocalDateTime horaInicial, final LocalDateTime horaFinal, String codigo, String nome, String descricao, List<Equipamento> equipamentosNecessarios) {
        super(horaInicial, horaFinal);
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.equipamentosNecessarios = equipamentosNecessarios;
    }

    public void realizarExame(final Paciente paciente) {
        equipamentosNecessarios.forEach(Equipamento::utilizar);
    }

    @Override
    protected void iniciar() {

    }

    @Override
    protected void terminar() {

    }
}
