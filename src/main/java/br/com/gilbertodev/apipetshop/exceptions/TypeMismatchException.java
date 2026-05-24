package br.com.gilbertodev.apipetshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TypeMismatchException extends RuntimeException {
    private final String codigo;
}
