package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.usuario.CriaUsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(CriaUsuarioRequestDTO dto) {
        var senhaCriptografada = passwordEncoder.encode(dto.password());
        var novoUsuario = new Usuario(dto.login(), senhaCriptografada);
        repository.save(novoUsuario);
    }
}