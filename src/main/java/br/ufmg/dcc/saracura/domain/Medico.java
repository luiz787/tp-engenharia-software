package br.ufmg.dcc.saracura.domain;

import br.ufmg.dcc.saracura.exception.BusinessException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javatuples.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Medico extends Funcionario {

    private final String crm;
    private final Especialidade especialidade;
    private final Agenda<Consulta> agenda;

    public Medico(String cpf, String nome, LocalDate dataNascimento, long salarioInicialCentavos,
                  final String crm, final Especialidade especialidade) {
        super(cpf, nome, dataNascimento, salarioInicialCentavos);
        this.crm = crm;
        this.especialidade = especialidade;
        this.agenda = new Agenda<>();
    }

    public void consultar(final Paciente paciente) {
        // Alguma lógica relativa à consulta estaria aqui.

        // Buscando a consulta da agenda do médico, para adicioná-la ao histórico do paciente.
        final var consulta = agenda.getEventos()
                .stream()
                .filter(c -> c.getPaciente().equals(paciente))
                .findFirst()
                .orElseThrow(()-> new BusinessException("Consulta para o paciente CPF " + paciente.getCpf() + " não encontrada."));
        paciente.adicionarConsultaHistorico(consulta);
    }

    public void adicionarConsultaAgenda(final Consulta consulta) {
        agenda.adicionarEvento(consulta);
    }

    public boolean disponivel(final LocalDateTime horaInicial, final LocalDateTime horaFinal) {
        return agenda.verificarDisponibilidade(Pair.with(horaInicial, horaFinal));
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }
}
