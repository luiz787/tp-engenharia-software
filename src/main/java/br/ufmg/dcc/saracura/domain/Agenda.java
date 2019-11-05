package br.ufmg.dcc.saracura.domain;

import br.ufmg.dcc.saracura.exception.BusinessException;
import org.javatuples.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {

    private List<Consulta> consultas;

    public Agenda() {
        this.consultas = new ArrayList<>();
    }

    public void adicionarConsulta(final Consulta consulta) {
        if (verificarDisponibilidade(consulta.getHorarios())) {
            consultas.add(consulta);
        } else {
            throw new BusinessException("A consulta não foi adicionada porque não há disponibilidade no horário.");
        }
    }

    public void removerConsulta(final Consulta consulta) {
        consultas.remove(consulta);
    }

    public boolean verificarDisponibilidade(final Pair<LocalDateTime, LocalDateTime> horarios) {
        var copiaConsultas = List.copyOf(consultas);
        var listaHorariosConsultas = copiaConsultas
                .stream()
                .map(Consulta::getHorarios)
                .collect(Collectors.toList());
        listaHorariosConsultas.add(horarios);
        var horariosOrdenados = listaHorariosConsultas
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
