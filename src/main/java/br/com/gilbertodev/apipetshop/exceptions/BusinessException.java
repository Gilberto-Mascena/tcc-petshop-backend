package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String codigo;

    public BusinessException(MessageBase mensagemBase) {

        super(mensagemBase.getMensagem());
        this.codigo = mensagemBase.getCodigo();
    }
}
