package br.com.gilbertodev.apipetshop.enums;

import lombok.Getter;

@Getter
public enum PortePet {

    PEQUENO("Pequeno"),
    MEDIO("Médio"),
    GRANDE("Grande");

    private final String descricao;

    PortePet(String descricao) {
        this.descricao = descricao;
    }
}
