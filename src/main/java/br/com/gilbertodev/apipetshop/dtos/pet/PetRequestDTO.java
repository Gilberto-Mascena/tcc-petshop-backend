package br.com.gilbertodev.apipetshop.dtos.pet;

import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.enums.PortePet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDTO {

    @NotBlank(message = "O nome do pet é obrigatório")
    @Size(min = 2, max = 50, message = "O nome do pet deve conter entre 2 e 50 caracteres")
    private String nome;

    @NotBlank(message = "A espécie do pet é obrigatória")
    @Size(min = 2, max = 30, message = "A espécie do pet deve conter entre 2 e 30 caracteres")
    private String especie;

    private String raca;

    @NotNull(message = "A data de nascimento do pet é obrigatória")
    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    private LocalDate dataNascimento;
    private String observacoes;

    @NotNull(message = "O porte do pet é obrigatório")
    private PortePet porte;

    public Pet toEntity() {
        Pet pet = new Pet();

        pet.setNome(this.nome);
        pet.setEspecie(this.especie);
        pet.setRaca(this.raca);
        pet.setDataNascimento(this.dataNascimento);
        pet.setObservacoes(this.observacoes);
        pet.setPorte(this.porte);

        return pet;
    }
}
