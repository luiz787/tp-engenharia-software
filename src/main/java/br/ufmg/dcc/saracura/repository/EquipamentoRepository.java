package br.ufmg.dcc.saracura.repository;

import br.ufmg.dcc.saracura.domain.Equipamento;

import java.util.List;

public interface EquipamentoRepository {
    List<Equipamento> obterEquipamentos();
}
