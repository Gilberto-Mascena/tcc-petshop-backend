package br.com.gilbertodev.apipetshop.dtos.agendamento;

import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.enums.TipoServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime dataHora,
        StatusAgendamento status,
        String nomePet,
        String nomeTutor,
        TipoServico tipoServico,
        String observacoes,
        BigDecimal valorTotal
) {
}
