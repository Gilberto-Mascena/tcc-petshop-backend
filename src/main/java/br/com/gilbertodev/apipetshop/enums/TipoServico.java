package br.com.gilbertodev.apipetshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoServico {

    BANHO("Banho"),
    TOSA("Tosa"),
    BANHO_E_TOSA("Banho e Tosa"),
    CORTE_UNHA("Corte de Unha"),
    LIMPEZA_OUVIDO("Limpeza de Ouvido");

    private final String descricao;
}
