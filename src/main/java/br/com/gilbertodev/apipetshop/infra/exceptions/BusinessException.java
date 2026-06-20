package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String codigo;

    public BusinessException(MessageBase messageBase) {
        super(messageBase.getMensagem());
        this.codigo = messageBase.getCodigo();
    }
}
