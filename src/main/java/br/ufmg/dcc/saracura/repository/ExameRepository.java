package br.ufmg.dcc.saracura.repository;

import br.ufmg.dcc.saracura.domain.Exame;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository {
    Exame cadastrarExame(Exame exame);
}
