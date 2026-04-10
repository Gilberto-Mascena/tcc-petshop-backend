package br.com.gilbertodev.apipetshop.enums;

import lombok.Getter;

@Getter
public enum TipoServico {

    BANHO("Banho"),
    TOSA("Tosa"),
    BANHO_E_TOSA("Banho e Tosa"),
    CORTE_UNHA("Corte de Unha"),
    LIMPEZA_OUVIDO("Limpeza de Ouvido");

    private final String descricao;

    TipoServico(String descricao) {
        this.descricao = descricao;
    }
}
