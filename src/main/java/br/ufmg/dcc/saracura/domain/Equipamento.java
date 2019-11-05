package br.ufmg.dcc.saracura.domain;

import org.javatuples.Pair;

import java.time.LocalDateTime;

public class Equipamento {

    private final String codigo;
    private final String nome;
    private final String descricao;
    private final TipoEquipamento tipo;
    private final Agenda agenda;

    public Equipamento(final String codigo, final String nome, final String descricao, final TipoEquipamento tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.agenda = new Agenda();
    }

    public boolean verificarDisponibilidade(final LocalDateTime horaInicial, final LocalDateTime horaFinal) {
        return agenda.verificarDisponibilidade(Pair.with(horaInicial, horaFinal));
    }

    public void adicionarExameAgenda(final Exame exame) {
        agenda.adicionarEvento(exame);
    }

    public void utilizar() {
        // TODO: impl
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

}
