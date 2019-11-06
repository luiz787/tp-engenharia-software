package br.ufmg.dcc.saracura.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Exame extends Evento {

    private final String codigo;
    private final Paciente paciente;
    private final List<Equipamento> equipamentos;

    public Exame(final Paciente paciente, final LocalDateTime horaInicial, final LocalDateTime horaFinal, final List<Equipamento> equipamentos) {
        super(horaInicial, horaFinal);
        this.codigo = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.equipamentos = equipamentos;
        this.equipamentos.forEach(e -> e.adicionarExameAgenda(this));
    }

    @Override
    protected void iniciar() {
        equipamentos.forEach(Equipamento::utilizar);
    }

    @Override
    protected void terminar() {

    }

    public String getCodigo() {
        return codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }
}
