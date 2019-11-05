package br.ufmg.dcc.saracura.repository;

import br.ufmg.dcc.saracura.domain.Consulta;

public interface ConsultaRepository {
    Consulta persistirConsulta(Consulta consulta);
}
