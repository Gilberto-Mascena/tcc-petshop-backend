package br.com.gilbertodev.apipetshop.security.controller;

import br.com.gilbertodev.apipetshop.doc.UsuarioAuthControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.autenticacao.UsuarioLoginRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.autenticacao.UsuarioTokenResponseDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.security.token.TokenService;
import br.com.gilbertodev.apipetshop.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UsuarioAuthController implements UsuarioAuthControllerDoc {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public UsuarioAuthController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenResponseDTO> efetuarLogin(@RequestBody @Valid UsuarioLoginRequestDTO dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new UsuarioTokenResponseDTO(tokenJWT));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> autoCadastro(@RequestBody @Valid UsuarioRequestDTO dados) {
        UsuarioResponseDTO novoCliente = usuarioService.cadastrarCliente(dados);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.id())
                .toUri();
        return ResponseEntity.created(uri).body(novoCliente);
    }
}