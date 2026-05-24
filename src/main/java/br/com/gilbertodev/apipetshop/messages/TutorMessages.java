package br.com.gilbertodev.apipetshop.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TutorMessages implements MessageBase {

    TUTOR_NAO_ENCONTRADO("TUTOR_001", "Tutor não encontrado."),
    CPF_JA_CADASTRADO("TUTOR_002", "Já existe um tutor cadastrado com este CPF."),
    EMAIL_INVALIDO("TUTOR_003", "O formato do e-mail informado é inválido."),
    TERMO_BUSCA_CURTO("TUTOR_004", "O termo de busca deve conter pelo menos 3 caracteres.");

    private final String codigo;
    private final String mensagem;
}
