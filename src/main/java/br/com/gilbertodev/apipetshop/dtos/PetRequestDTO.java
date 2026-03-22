package br.com.gilbertodev.apipetshop.dtos;

import br.com.gilbertodev.apipetshop.entities.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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

    @PositiveOrZero(message = "A idade do pet deve ser um número positivo ou zero")
    private Integer idade;
    private String observacoes;

    public Pet toEntity() {
        Pet pet = new Pet();
        BeanUtils.copyProperties(this, pet);
        return pet;
    }
}
