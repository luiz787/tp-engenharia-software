package br.ufmg.dcc.saracura.dto;

import br.ufmg.dcc.saracura.domain.TipoExame;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequisicaoExame {
    private final String cpf;
    private final TipoExame tipoExame;
    private final LocalDateTime horarioInicial;
}
