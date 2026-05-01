package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.enums.messages.UsuarioMessages;
import br.com.gilbertodev.apipetshop.exceptions.AuthException;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new AuthException(UsuarioMessages.USUARIO_NAO_ENCONTRADO);
        }
        return usuario;
    }
}