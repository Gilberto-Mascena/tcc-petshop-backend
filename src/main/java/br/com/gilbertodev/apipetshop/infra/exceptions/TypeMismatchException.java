package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class TypeMismatchException extends RuntimeException {

    private final String codigo;

    public TypeMismatchException(MessageBase messageBase) {
        super(messageBase.getMensagem());
        this.codigo = messageBase.getCodigo();
    }
}
