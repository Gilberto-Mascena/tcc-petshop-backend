package br.com.gilbertodev.apipetshop.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroRequestDTO(

        @NotBlank(message = "O campo 'login' é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
        String password
) {
}
