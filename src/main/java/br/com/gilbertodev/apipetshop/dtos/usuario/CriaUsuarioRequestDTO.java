package br.com.gilbertodev.apipetshop.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriaUsuarioRequestDTO(

        @NotBlank(message = "O login é obrigatório")
        @Size(min = 4, max = 20, message = "O login deve ter entre 4 e 20 caracteres")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password
) {
}
