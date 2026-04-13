package br.com.gilbertodev.apipetshop.enums;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServicoMessages implements MessageBase {

    PET_OU_PORTE_NULO("SERV-001", "O pet e seu porte devem estar vinculados para o cálculo."),
    TIPO_SERVICO_NULO("SERV-002", "O tipo de serviço deve ser informado.");

    private final String codigo;
    private final String mensagem;
}
