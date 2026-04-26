package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.usuario.CriaUsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrarUsuario(@Valid @RequestBody CriaUsuarioRequestDTO criaUsuarioRequestDTO) {

        var senhaCriptografada = passwordEncoder.encode(criaUsuarioRequestDTO.getPassword());
        var novoUsuario = new Usuario(criaUsuarioRequestDTO.getLogin(), senhaCriptografada);

        usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
