package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.enums.TipoServico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoRequestDTO(
        @NotNull(message = "O tipo de serviço é obrigatório")
        TipoServico tipo,

        String observacoes,

        @NotNull(message = "O valor base é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valorBase
) {
}