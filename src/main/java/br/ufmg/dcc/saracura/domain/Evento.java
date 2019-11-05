package br.ufmg.dcc.saracura.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javatuples.Pair;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public abstract class Evento {

    private final LocalDateTime horaInicial;
    private final LocalDateTime horaFinal;

    protected Evento(final LocalDateTime horaInicial, final LocalDateTime horaFinal) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }

    public Pair<LocalDateTime, LocalDateTime> getHorarios() {
        return Pair.with(horaInicial, horaFinal);
    }

    protected abstract void iniciar();

    protected abstract void terminar();
}
