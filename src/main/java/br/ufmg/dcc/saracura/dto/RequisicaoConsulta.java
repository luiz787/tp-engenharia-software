package br.ufmg.dcc.saracura.dto;

import br.ufmg.dcc.saracura.domain.Especialidade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequisicaoConsulta {
    private final String cpf;
    private final LocalDateTime horaInicial;
    private final LocalDateTime horaFinal;
    private final Especialidade especialidade;
}
