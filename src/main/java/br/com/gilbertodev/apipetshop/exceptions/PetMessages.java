package br.com.gilbertodev.apipetshop.exceptions;

import lombok.Getter;

@Getter
public enum PetMessages {

    PET_IDADE_INVALIDA("PET_001", "O pet não pode ter mais de 30 anos."),
    PET_NAO_ENCONTRADO("PET_002", "Pet não localizado.");


    private final String codigo;
    private final String mensagem;

    PetMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
