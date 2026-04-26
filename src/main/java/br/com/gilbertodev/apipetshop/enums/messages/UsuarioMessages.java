package br.com.gilbertodev.apipetshop.enums.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum UsuarioMessages implements MessageBase {

    USUARIO_NAO_ENCONTRADO("USUARIO_001", "Login ou senha inválidos.");

    private final String codigo;
    private final String mensagem;

    UsuarioMessages(String codigo, String descricao) {
        this.codigo = codigo;
        this.mensagem = descricao;
    }
}
