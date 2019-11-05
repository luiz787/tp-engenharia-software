package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.EquipeMedica;
import br.ufmg.dcc.saracura.domain.Especialidade;
import br.ufmg.dcc.saracura.domain.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final EquipeMedica equipeMedica;

    public Medico cadastrarMedico(final Medico medico) {
        return equipeMedica.adicionarMedico(medico);
    }

    public void excluirMedico(final String crm) {
        equipeMedica.removerMedico(crm);
    }

    public Medico obterMedico(final String crm) {
        return equipeMedica.obterMedico(crm);
    }

    public List<Medico> obterMedicosDisponiveisPeriodo(final LocalDateTime horaInicial, final LocalDateTime horaFinal,
                                                       final Especialidade especialidade) {
        return equipeMedica.obterMedicosDisponiveisPeriodo(horaInicial, horaFinal, especialidade);
    }
}
