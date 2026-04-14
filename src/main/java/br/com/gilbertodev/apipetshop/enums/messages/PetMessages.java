package br.com.gilbertodev.apipetshop.enums.messages;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import lombok.Getter;

@Getter
public enum PetMessages implements MessageBase {

    PET_IDADE_INVALIDA("PET_001", "O pet não pode ter mais de 30 anos."),
    PET_NAO_ENCONTRADO("PET_002", "Pet não localizado."),
    PORTE_OBRIGATORIO("PET-003", "O porte do pet é obrigatório para realizar esta operação."),
    TUTOR_NAO_VINCULADO("PET-004", "O pet deve estar obrigatoriamente vinculado a um tutor.");


    private final String codigo;
    private final String mensagem;

    PetMessages(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
