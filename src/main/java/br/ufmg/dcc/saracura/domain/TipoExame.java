package br.ufmg.dcc.saracura.domain;

import java.time.Duration;
import java.util.EnumSet;

public enum TipoExame {

    ANALISE_LIQUIDO(Duration.ofMinutes(30), EnumSet.of(TipoEquipamento.COLETOR_SANGUINEO)),
    ANALISE_MATERIAL_GENETICO(Duration.ofMinutes(60), EnumSet.of(TipoEquipamento.MICROSCOPIO)),
    ENDOSCOPIA(Duration.ofMinutes(120), EnumSet.of(TipoEquipamento.EQUIPAMENTO_ENDOSCOPIA)),
    IMAGEM(Duration.ofMinutes(60), EnumSet.of(TipoEquipamento.EQUIPAMENTO_ULTRASSOM));

    private final Duration duracao;
    private final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios;

    TipoExame(final Duration duracao, final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios) {
        this.duracao = duracao;
        this.tiposEquipamentosNecessarios = tiposEquipamentosNecessarios;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public EnumSet<TipoEquipamento> getTiposEquipamentosNecessarios() {
        return tiposEquipamentosNecessarios;
    }
}
