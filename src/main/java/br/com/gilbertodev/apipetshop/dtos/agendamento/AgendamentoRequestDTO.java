package br.com.gilbertodev.apipetshop.dtos.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "O agendamento deve ser para uma data futura")
        LocalDateTime dataHora,

        @NotNull(message = "O ID do pet é obrigatório")
        Long petId,

        @NotNull(message = "O ID do serviço é obrigatório")
        Long servicoId,

        String observacoes
) {
}
