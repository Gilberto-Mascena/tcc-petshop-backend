package br.com.gilbertodev.apipetshop.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum FuncionarioMessages implements MessageBase {

    FUNCIONARIO_NAO_ENCONTRADO("FUNCIONARIO_001", "Funcionário não encontrado."),
    CPF_JA_CADASTRADO("FUNCIONARIO_002", "CPF já cadastrado."),
    EMAIL_JA_CADASTRADO("FUNCIONARIO_003", "Email já cadastrado."),
    LOGIN_JA_CADASTRADO("FUNCIONARIO_004", "Login já cadastrado."),
    TERMO_BUSCA_CURTO("FUNCIONARIO_005", "O termo de busca deve conter pelo menos 3 caracteres."),
    ROLE_NAO_ENCONTRADA("FUNCIONARIO_006", "Perfil de acesso não encontrado.");

    private final String codigo;
    private final String mensagem;

    FuncionarioMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
