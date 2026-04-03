package br.com.gilbertodev.apipetshop.exceptions;

import java.time.Instant;

public record StandardError(
        Instant dataHora,
        Integer status,
        String codigo,
        String erro,
        String mensagem,
        String caminho
) {
}
