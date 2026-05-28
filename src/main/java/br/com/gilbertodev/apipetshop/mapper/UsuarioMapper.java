package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRequestDTO == null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioRequestDTO.login());
        usuario.setSenha(usuarioRequestDTO.password());

        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin()
        );
    }

    public void atualizarDados(UsuarioRequestDTO dto, Usuario usuario) {
        if (dto == null || usuario == null) return;

        if (dto.login() != null) usuario.setLogin(dto.login());
        if (dto.password() != null) usuario.setSenha(dto.password());
    }
}
