package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.enums.TipoServico;

import java.math.BigDecimal;

public record ServicoResponseDTO(
        Long id,
        TipoServico tipoServico,
        String observacoes,
        BigDecimal valorBase
) {
}