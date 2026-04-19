package br.com.gilbertodev.apipetshop.dtos.agendamento;

import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgendamentoResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String nomePet;
    private String tipoServico;
    private String observacoes;

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.dataHora = agendamento.getDataHora();
        this.status = agendamento.getStatus();

        if (agendamento.getPet() != null) {
            this.nomePet = agendamento.getPet().getNome();
        }

        if (agendamento.getServico() != null) {
            this.tipoServico = agendamento.getServico().getTipo().name();
        }

        this.observacoes = agendamento.getObservacoes();
    }
}
