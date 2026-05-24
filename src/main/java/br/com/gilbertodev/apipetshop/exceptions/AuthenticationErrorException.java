package br.com.gilbertodev.apipetshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationErrorException extends RuntimeException {
    private final String codigo;
}
