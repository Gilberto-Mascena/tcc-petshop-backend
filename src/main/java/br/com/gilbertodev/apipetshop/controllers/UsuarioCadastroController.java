package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.UsuarioCadastroControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioCadastroRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioCadastroController implements UsuarioCadastroControllerDoc {

    private final UsuarioService usuarioService;

    public UsuarioCadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> autoCadastro(@RequestBody @Valid UsuarioCadastroRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarCliente(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
