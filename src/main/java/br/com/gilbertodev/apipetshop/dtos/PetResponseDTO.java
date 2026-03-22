package br.com.gilbertodev.apipetshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PetResponseDTO {

    private final Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final Integer idade;
    private final String observacoes;
}
