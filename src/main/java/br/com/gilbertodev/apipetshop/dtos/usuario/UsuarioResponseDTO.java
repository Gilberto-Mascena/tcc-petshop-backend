package br.com.gilbertodev.apipetshop.dtos.usuario;

import java.util.Set;

public record UsuarioResponseDTO(

        Long id,
        String login,
        Set<String> roles
) {
}
