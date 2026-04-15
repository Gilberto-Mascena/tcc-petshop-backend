package br.com.gilbertodev.apipetshop.enums;

import lombok.Getter;

@Getter
public enum StatusAgendamento {

    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    REALIZADO("Realizado");

    private final String descricao;

    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }
}
