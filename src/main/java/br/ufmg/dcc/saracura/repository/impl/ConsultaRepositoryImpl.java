package br.ufmg.dcc.saracura.repository.impl;

import br.ufmg.dcc.saracura.domain.Consulta;
import br.ufmg.dcc.saracura.repository.ConsultaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ConsultaRepositoryImpl implements ConsultaRepository {

    private final Map<String, Consulta> consultas;

    public ConsultaRepositoryImpl() {
        consultas = new ConcurrentHashMap<>();
    }

    @Override
    public Consulta persistirConsulta(final Consulta consulta) {
        consultas.put(consulta.getId(), consulta);
        return consultas.get(consulta.getId());
    }
}
