package br.com.gilbertodev.apipetshop.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum ResourceMessages implements MessageBase {

    ROLE_PADRAO_NAO_ENCONTRADA("INFRA-001", "A permissão padrão (ROLE_ATENDENTE) não foi encontrada na base de dados.");

    private final String codigo;
    private final String mensagem;

    ResourceMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
