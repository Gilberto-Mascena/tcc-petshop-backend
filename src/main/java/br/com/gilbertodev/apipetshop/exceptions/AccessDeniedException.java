package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {

    private final String codigo;

    public AccessDeniedException(MessageBase mensagemBase) {
        super(mensagemBase.getMensagem());
        this.codigo = mensagemBase.getCodigo();
    }
}