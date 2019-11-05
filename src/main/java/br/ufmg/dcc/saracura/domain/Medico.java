package br.ufmg.dcc.saracura.domain;

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
    private final Agenda agenda;

    public Medico(String cpf, String nome, LocalDate dataNascimento, long salarioInicialCentavos,
                  final String crm, final Especialidade especialidade) {
        super(cpf, nome, dataNascimento, salarioInicialCentavos);
        this.crm = crm;
        this.especialidade = especialidade;
        this.agenda = new Agenda();
    }

    public void Consultar(final Paciente paciente) {
        // TODO: implementar
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
