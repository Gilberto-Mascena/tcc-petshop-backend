package br.com.gilbertodev.apipetshop.dtos.pet;

import br.com.gilbertodev.apipetshop.enums.PortePet;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PetRequestDTO(
        @NotBlank(message = "O nome do pet é obrigatório")
        String nome,

        @NotBlank(message = "A espécie é obrigatória")
        String especie,

        String raca,

        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        LocalDate dataNascimento,

        String observacoes,

        @NotNull(message = "O ID do tutor é obrigatório")
        Long tutorId,

        @NotNull(message = "O porte do pet é obrigatório")
        PortePet porte
) {}