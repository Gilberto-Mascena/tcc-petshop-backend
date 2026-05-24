package br.com.gilbertodev.apipetshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusAgendamento {

    AGENDADO("Agendado"),
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    REALIZADO("Realizado");

    private final String descricao;
}
