package br.com.gilbertodev.apipetshop.exceptions;

public record ValidationErrorDetails(
        String campo,
        String mensagem
) {
}
