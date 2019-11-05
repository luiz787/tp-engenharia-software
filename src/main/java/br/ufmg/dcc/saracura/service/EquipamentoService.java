package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.Equipamento;
import br.ufmg.dcc.saracura.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public List<Equipamento> obterEquipamentos() {
        return equipamentoRepository.obterEquipamentos();
    }

}
