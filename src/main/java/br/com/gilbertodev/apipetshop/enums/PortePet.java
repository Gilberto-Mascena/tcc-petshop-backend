package br.com.gilbertodev.apipetshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PortePet {

    PEQUENO("Pequeno"),
    MEDIO("Médio"),
    GRANDE("Grande");

    private final String descricao;
}
