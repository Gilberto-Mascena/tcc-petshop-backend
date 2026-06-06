package br.com.gilbertodev.apipetshop.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum ServicoMessages implements MessageBase {

    PET_OU_PORTE_NULO("SERV_001", "O pet e seu porte devem estar vinculados para o cálculo."),
    TIPO_SERVICO_NULO("SERV_002", "O tipo de serviço deve ser informado."),
    SERVICO_NAO_ENCONTRADO("SERV_003", "Serviço não encontrado."),
    TERMO_BUSCA_CURTO("SERV_004", "O termo de busca deve conter pelo menos 3 caracteres.");

    private final String codigo;
    private final String mensagem;

    ServicoMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
