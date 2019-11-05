package br.ufmg.dcc.saracura.repository.impl;

import br.ufmg.dcc.saracura.domain.Exame;
import br.ufmg.dcc.saracura.repository.ExameRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExameRepositoryImpl implements ExameRepository {

    private Map<String, Exame> exames;

    public ExameRepositoryImpl() {
        exames = new ConcurrentHashMap<>();
    }

    @Override
    public Exame cadastrarExame(Exame exame) {
        exames.put(exame.getCodigo(), exame);
        return exames.get(exame.getCodigo());
    }
}
