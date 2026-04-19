package br.com.gilbertodev.apipetshop.dtos.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AgendamentoRequestDTO {

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "O agendamento deve ser para uma data futura")
    private LocalDateTime dataHora;

    @NotNull(message = "O ID do pet é obrigatório")
    private Long petId;

    @NotNull(message = "O ID do serviço é obrigatório")
    private Long servicoId;

    private String observacoes;
}
