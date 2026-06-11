package br.com.gilbertodev.apipetshop.security.service;

import br.com.gilbertodev.apipetshop.messages.UsuarioMessages;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAuthService implements UserDetailsService {

    private final UsuarioRepository repository;

    public UsuarioAuthService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var usuario = repository.findByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(UsuarioMessages.USUARIO_NAO_ENCONTRADO.getMensagem());
        }
        return usuario;
    }
}
