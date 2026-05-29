package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.autenticacao.DadosAutenticacao;
import br.com.gilbertodev.apipetshop.dtos.autenticacao.DadosTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Autenticação",
        description = "Endpoints para autenticação de usuários")
public interface AutenticacaoControllerDoc {

    @Operation(
            summary = "Realiza login",
            description = "Autentica usuário e retorna token JWT")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login bem sucedido"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas")
    })
    ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados);
}
