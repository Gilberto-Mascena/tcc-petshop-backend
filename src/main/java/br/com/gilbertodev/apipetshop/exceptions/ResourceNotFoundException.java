package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String codigo;

    public ResourceNotFoundException(MessageBase messageBase) {
        super(messageBase.getMensagem());
        this.codigo = messageBase.getCodigo();
    }
}
