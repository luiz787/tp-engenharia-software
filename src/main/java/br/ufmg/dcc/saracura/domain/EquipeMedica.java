package br.ufmg.dcc.saracura.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EquipeMedica {

    private Map<String, Medico> medicos;

    public EquipeMedica() {
        this.medicos = new ConcurrentHashMap<>();
    }

    public List<Medico> obterMedicosDisponiveisPeriodo(final LocalDateTime horaInicial, final LocalDateTime horaFinal,
                                                       final Especialidade especialidade) {
        return medicos
                .values()
                .stream()
                .filter(medico -> medico.disponivel(horaInicial, horaFinal))
                .filter(medico -> medico.getEspecialidade().equals(especialidade))
                .collect(Collectors.toList());
    }

    public Medico adicionarMedico(final Medico medico) {
        medicos.put(medico.getCrm(), medico);
        return medicos.get(medico.getCrm());
    }

    public Medico obterMedico(final String crm) {
        return medicos.get(crm);
    }

    public void removerMedico(final String crm) {
        medicos.remove(crm);
    }
}
