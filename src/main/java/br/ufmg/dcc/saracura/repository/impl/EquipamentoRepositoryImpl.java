package br.ufmg.dcc.saracura.repository.impl;

import br.ufmg.dcc.saracura.domain.Equipamento;
import br.ufmg.dcc.saracura.repository.EquipamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EquipamentoRepositoryImpl implements EquipamentoRepository {

    private Map<String, Equipamento> equipamentos;

    public EquipamentoRepositoryImpl() {
        equipamentos = new ConcurrentHashMap<>();
    }

    @Override
    public List<Equipamento> obterEquipamentos() {
        return List.copyOf(equipamentos.values());
    }
}
