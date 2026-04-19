package br.com.gilbertodev.apipetshop.dtos.pet;

import br.com.gilbertodev.apipetshop.entities.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // Adicionado para permitir a população dos dados
@NoArgsConstructor
public class PetResponseDTO {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String observacoes;
    private LocalDateTime dataCriacao;
    // Removi 'idade' e usei o padrão da entidade ou um cálculo se preferir

    // CONSTRUTOR QUE ELIMINA OS ERROS
    public PetResponseDTO(Pet pet) {
        this.id = pet.getId();           // Vem da EntidadeBase
        this.nome = pet.getNome();
        this.especie = pet.getEspecie();
        this.raca = pet.getRaca();
        this.observacoes = pet.getObservacoes();
        this.dataCriacao = pet.getDataCriacao(); // Vem da EntidadeBase
    }
}