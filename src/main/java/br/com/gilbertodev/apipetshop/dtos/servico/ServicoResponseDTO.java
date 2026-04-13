package br.com.gilbertodev.apipetshop.dtos.servico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ServicoResponseDTO {

    private final Long id;
    private final String tipoDescricao;
    private final BigDecimal valor;
    private final LocalDateTime dataHora;
    private final String nomePet;
}
