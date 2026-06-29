package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioCadastroRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Role;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRequestDTO == null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioRequestDTO.login());

        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) return null;

        Set<String> rolesNames = usuario.getRoles() == null ? Collections.emptySet() :
                usuario.getRoles().stream()
                        .map(Role::getNome)
                        .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin(),
                rolesNames
        );
    }

    public void atualizarDados(UsuarioRequestDTO dto, Usuario usuario) {
        if (dto == null || usuario == null) return;

        if (dto.login() != null && !dto.login().isBlank()) {
            usuario.setLogin(dto.login());
        }
    }

    public Usuario toEntityFromCadastro(UsuarioCadastroRequestDTO dto) {

        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenha(dto.password());
        return usuario;
    }
}