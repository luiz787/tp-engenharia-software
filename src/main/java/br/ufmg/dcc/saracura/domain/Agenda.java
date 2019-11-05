package br.ufmg.dcc.saracura.domain;

import br.ufmg.dcc.saracura.exception.BusinessException;
import org.javatuples.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {

    private List<Evento> eventos;

    public Agenda() {
        this.eventos = new ArrayList<>();
    }

    public void adicionarEvento(final Evento evento) {
        if (verificarDisponibilidade(evento.getHorarios())) {
            eventos.add(evento);
        } else {
            throw new BusinessException("O evento não foi adicionado porque não há disponibilidade no horário.");
        }
    }

    public void removerEvento(final Evento evento) {
        eventos.remove(evento);
    }

    public boolean verificarDisponibilidade(final Pair<LocalDateTime, LocalDateTime> horarios) {
        var copiaEventos = List.copyOf(eventos);
        var listaHorariosEventos = copiaEventos
                .stream()
                .map(Evento::getHorarios)
                .collect(Collectors.toList());
        listaHorariosEventos.add(horarios);
        var horariosOrdenados = listaHorariosEventos
                .stream()
                .sorted(Comparator.comparing(Pair::getValue0))
                .collect(Collectors.toList());
        boolean compativel = true;
        for (int i = 0; i < horariosOrdenados.size() - 1; ++i) {
            var primeiroPar = horariosOrdenados.get(i);
            var segundoPar = horariosOrdenados.get(i + 1);
            if (!primeiroPar.getValue1().isBefore(segundoPar.getValue0())) {
                compativel = false;
                break;
            }
        }
        return compativel;
    }
}
