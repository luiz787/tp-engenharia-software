package br.ufmg.dcc.saracura.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Exame extends Evento {

    private final String codigo;
    private final Paciente paciente;
    private final TipoExame tipo;
    private final List<Equipamento> equipamentos;

    public Exame(final Paciente paciente, final TipoExame tipo, final LocalDateTime horaInicial, final LocalDateTime horaFinal, final List<Equipamento> equipamentos) {
        super(horaInicial, horaFinal);
        this.codigo = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.tipo = tipo;
        this.equipamentos = equipamentos;
        this.equipamentos.forEach(e -> e.adicionarExameAgenda(this));
    }

    @Override
    protected void iniciar() {
        equipamentos.forEach(Equipamento::utilizar);
    }

    @Override
    protected void terminar() {
       /* O método apenas foi sobrescrito da classe pai, mas
        não é aplicavel
       */
    }

    public String getCodigo() {
        return codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public TipoExame getTipo() {
        return tipo;
    }
}
