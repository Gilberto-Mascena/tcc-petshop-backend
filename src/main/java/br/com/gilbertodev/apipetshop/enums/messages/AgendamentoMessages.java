package br.com.gilbertodev.apipetshop.enums.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum AgendamentoMessages implements MessageBase {

    AGENDAMENTO_NAO_ENCONTRADO("AGEND_001", "Agendamento não encontrado."),
    HORARIO_PASSADO("AGEND_002", "O horário do agendamento não pode ser no passado.");

    private final String codigo;
    private final String mensagem;

    AgendamentoMessages(String codigo, String mensagem) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }
}
