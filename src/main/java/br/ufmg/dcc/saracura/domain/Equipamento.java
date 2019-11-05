package br.ufmg.dcc.saracura.domain;

public class Equipamento {

    private final String codigo;
    private final String nome;
    private final String descricao;

    public Equipamento(final String codigo, final String nome, final String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
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

}
