package br.com.gilbertodev.apipetshop.exceptions;

import lombok.Getter;

@Getter
public enum TutorMessages {

    TUTOR_NAO_ENCONTRADO("TUTOR_001", "Tutor não encontrado."),
    CPF_JA_CADASTRADO("TUTOR_002", "Já existe um tutor cadastrado com este CPF.");


    private final String codigo;
    private final String mensagem;

    TutorMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
