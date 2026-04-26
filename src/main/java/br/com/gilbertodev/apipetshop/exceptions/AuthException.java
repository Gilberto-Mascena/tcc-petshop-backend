package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final String codigo;

    public AuthException(MessageBase mensagemBase) {
        super(mensagemBase.getMensagem());
        this.codigo = mensagemBase.getCodigo();
    }
}
