package br.com.gilbertodev.apipetshop.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum UsuarioMessages implements MessageBase {

    USUARIO_NAO_ENCONTRADO("USUARIO_001", "Login ou senha inválidos."),
    ERRO_ACESSO_NEGADO("USUARIO_002", "Acesso negado, você não tem permissão para acessar este recurso."),
    ERRO_AUTENTICACAO("USUARIO_003", "Erro de autenticação."),
    CREDENCIAIS_INVALIDAS("USUARIO_004", "Login ou senha inválidos."),
    LOGIN_JA_CADASTRADO("USUARIO_005", "Este nome de usuário já está sendo utilizado."),
    TERMO_BUSCA_CURTO("USUARIO_006", "O termo de busca deve conter pelo menos 3 caracteres."),
    ROLE_NAO_ENCONTRADA("USUARIO_007", "Perfil de acesso não encontrado.");

    private final String codigo;
    private final String mensagem;

    UsuarioMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
