package br.com.gilbertodev.apipetshop.exceptions;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    private final String codgio;

    public ObjectNotFoundException(TutorMessages mensagem) {
        super(mensagem.getMensagem());
        this.codgio = mensagem.getCodigo();
    }

    public ObjectNotFoundException(PetMessages mensagem) {
        super(mensagem.getMensagem());
        this.codgio = mensagem.getCodigo();
    }
}
