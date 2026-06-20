package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.UsuarioAuthControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.auth.UsuarioLoginRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.auth.UsuarioTokenResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.infra.security.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioAuthController implements UsuarioAuthControllerDoc {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public UsuarioAuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;

    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenResponseDTO> efetuarLogin(@RequestBody @Valid UsuarioLoginRequestDTO dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new UsuarioTokenResponseDTO(tokenJWT));
    }
}