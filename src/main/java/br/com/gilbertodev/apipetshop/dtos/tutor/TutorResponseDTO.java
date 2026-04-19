package br.com.gilbertodev.apipetshop.dtos.tutor;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoResponseDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TutorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String celular;
    private EnderecoResponseDTO endereco;
    private List<PetResponseDTO> pets;

    public TutorResponseDTO(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.email = tutor.getEmail();
        this.celular = tutor.getCelular();

        if (tutor.getEndereco() != null) {
            this.endereco = new EnderecoResponseDTO(tutor.getEndereco());
        }

        if (tutor.getPets() != null) {
            this.pets = tutor.getPets().stream()
                    .map(PetResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}