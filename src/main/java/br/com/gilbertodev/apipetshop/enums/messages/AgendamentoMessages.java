package br.com.gilbertodev.apipetshop.enums.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum AgendamentoMessages implements MessageBase {

    AGENDAMENTO_NAO_ENCONTRADO("AGEND_001", "Agendamento não encontrado."),
    HORARIO_PASSADO("AGEND_002", "O horário do agendamento não pode ser no passado."),
    DADOS_INCOMPLETOS_PARA_CALCULO("AGEND_003", "Não é possível calcular o valor: Pet ou Serviço não informados."),
    STATUS_NAO_PODE_SER_ALTERADO("AGEND_004", "O status do agendamento não pode ser alterado para o status atual.");

    private final String codigo;
    private final String mensagem;

    AgendamentoMessages(String codigo, String mensagem) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }
}
