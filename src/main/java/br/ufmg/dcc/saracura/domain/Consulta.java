package br.ufmg.dcc.saracura.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Consulta extends Evento {

    private final String id;
    private final Medico medico;
    private final Paciente paciente;
    private StatusConsulta status;

    public Consulta(final LocalDateTime horaInicial, final LocalDateTime horaFinal, final Medico medico,
                    final Paciente paciente) {
        super(horaInicial, horaFinal);
        this.id = UUID.randomUUID().toString();
        this.medico = medico;
        this.paciente = paciente;
        this.status = StatusConsulta.AGENDADA;
        this.medico.adicionarConsultaAgenda(this);
    }

    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    public void finalizarConsulta() {
        this.status = StatusConsulta.REALIZADA;
    }

    @Override
    protected void iniciar() {
        this.status = StatusConsulta.EM_ANDAMENTO;
    }

    @Override
    protected void terminar() {
        finalizarConsulta();
    }

    public String getId() {
        return id;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public StatusConsulta getStatus() {
        return status;
    }
}
