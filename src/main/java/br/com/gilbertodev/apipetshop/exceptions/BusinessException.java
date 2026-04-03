package br.com.gilbertodev.apipetshop.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String codigo;

    public BusinessException(PetMessages mensagem) {

        super(mensagem.getMensagem());
        this.codigo = mensagem.getCodigo();
    }
}
