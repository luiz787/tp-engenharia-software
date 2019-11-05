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
        final var equipamentos = equipamentoService.obterEquipamentos();
        final var tiposEquipamentosNecessarios = requisicaoExame.getTipoExame().getTiposEquipamentosNecessarios();
        final var equipamentosPorTipo = obterEquipamentosTiposRelevantesAgrupadosPorTipo(equipamentos, tiposEquipamentosNecessarios);
        final var horarioFinal = obterHorarioFinalExame(requisicaoExame);
        final var equipamentosReservados = reservarEquipamentosDisponiveis(requisicaoExame, tiposEquipamentosNecessarios,
                equipamentosPorTipo, horarioFinal);
        final var paciente = pacienteService.consultarPacientePorCpf(cpf);
        final var exame = new Exame(paciente, requisicaoExame.getHorarioInicial(), horarioFinal, equipamentosReservados);
        return exameRepository.cadastrarExame(exame);
    }

    private ArrayList<Equipamento> reservarEquipamentosDisponiveis(final RequisicaoExame requisicaoExame,
                                                                   final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios,
                                                                   final Map<TipoEquipamento, List<Equipamento>> equipamentosPorTipo,
                                                                   final LocalDateTime horarioFinal) {
        final var equipamentosReservados = new ArrayList<Equipamento>(tiposEquipamentosNecessarios.size());
        for (var entrada : equipamentosPorTipo.entrySet()) {
            final var equipamento = obterAlgumEquipamentoDisponivel(requisicaoExame, horarioFinal, entrada);
            equipamentosReservados.add(equipamento);
        }
        return equipamentosReservados;
    }

    private LocalDateTime obterHorarioFinalExame(RequisicaoExame requisicaoExame) {
        return requisicaoExame
                    .getHorarioInicial()
                    .plusMinutes(requisicaoExame.getTipoExame().getDuracao().toMinutes());
    }

    private Map<TipoEquipamento, List<Equipamento>> obterEquipamentosTiposRelevantesAgrupadosPorTipo(
            final List<Equipamento> equipamentos, final EnumSet<TipoEquipamento> tiposEquipamentosNecessarios) {
        final var equipamentosTiposRelevantes = equipamentos
                .stream()
                .filter(equipamento -> tiposEquipamentosNecessarios.contains(equipamento.getTipo()))
                .collect(Collectors.toList());
        return equipamentosTiposRelevantes
                .stream()
                .collect(Collectors.groupingBy(Equipamento::getTipo));
    }

    private Equipamento obterAlgumEquipamentoDisponivel(final RequisicaoExame requisicaoExame,
                                                        final LocalDateTime horarioFinal,
                                                        final Map.Entry<TipoEquipamento, List<Equipamento>> entrada) {
        return entrada.getValue()
                .stream()
                .filter(e -> e.verificarDisponibilidade(requisicaoExame.getHorarioInicial(), horarioFinal))
                .findFirst()
                .orElseThrow(()-> new BusinessException("Não foi possível marcar o exame pois não há nenhum equipamento do tipo "
                        + entrada.getKey() + " disponível."));
    }
}
