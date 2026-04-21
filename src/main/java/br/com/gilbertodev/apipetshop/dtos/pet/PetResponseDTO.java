package br.com.gilbertodev.apipetshop.dtos.pet;

import br.com.gilbertodev.apipetshop.entities.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
public class PetResponseDTO {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String porte;
    private Integer idade;
    private String observacoes;
    private LocalDateTime dataCriacao;

    public PetResponseDTO(Pet pet) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.especie = pet.getEspecie();
        this.raca = pet.getRaca();
        this.observacoes = pet.getObservacoes();
        this.dataCriacao = pet.getDataCriacao();

        this.porte = (pet.getPorte() != null) ? pet.getPorte().getDescricao() : null;

        this.idade = calcularIdade(pet.getDataNascimento());
    }

    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }

        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}