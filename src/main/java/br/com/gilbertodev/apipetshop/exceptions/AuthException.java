package br.com.gilbertodev.apipetshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException {
    private final String codigo;
}
