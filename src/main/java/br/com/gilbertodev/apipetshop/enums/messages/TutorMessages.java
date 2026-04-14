package br.com.gilbertodev.apipetshop.enums.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum TutorMessages implements MessageBase {

    TUTOR_NAO_ENCONTRADO("TUTOR_001", "Tutor não encontrado."),
    CPF_JA_CADASTRADO("TUTOR_002", "Já existe um tutor cadastrado com este CPF."),
    EMAIL_INVALIDO("TUT-003", "O formato do e-mail informado é inválido.");


    private final String codigo;
    private final String mensagem;

    TutorMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
