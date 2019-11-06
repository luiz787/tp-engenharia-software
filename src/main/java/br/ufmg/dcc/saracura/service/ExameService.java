package br.ufmg.dcc.saracura.service;

import br.ufmg.dcc.saracura.domain.Equipamento;
import br.ufmg.dcc.saracura.domain.Exame;
import br.ufmg.dcc.saracura.domain.TipoEquipamento;
import br.ufmg.dcc.saracura.dto.RequisicaoExame;
import br.ufmg.dcc.saracura.exception.BusinessException;
import br.ufmg.dcc.saracura.repository.ExameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExameService {

    private final ExameRepository exameRepository;
    private final EquipamentoService equipamentoService;
    private final PacienteService pacienteService;

    public Exame marcarExame(final RequisicaoExame requisicaoExame) {
        final var cpf = requisicaoExame.getCpf();
        final var paciente = pacienteService.consultarPacientePorCpf(cpf);
        return paciente.solicitarExame(requisicaoExame);
    }
}
