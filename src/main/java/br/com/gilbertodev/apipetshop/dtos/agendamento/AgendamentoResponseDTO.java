package br.com.gilbertodev.apipetshop.dtos.agendamento;

import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal valorTotal;

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

        if (agendamento.getServico() != null) {
            this.tipoServico = agendamento.getServico().getTipo().getDescricao();
        }

        this.observacoes = agendamento.getObservacoes();
        this.valorTotal = agendamento.getValorTotal();
    }
}
