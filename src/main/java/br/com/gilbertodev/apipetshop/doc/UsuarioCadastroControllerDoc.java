package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioCadastroRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autocadastro", description = "Endpoint público para novos clientes se registrarem no sistema")
public interface UsuarioCadastroControllerDoc {

    @Operation(summary = "Realizar o autocadastro de um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Login já cadastrado no sistema")
    })
    ResponseEntity<UsuarioResponseDTO> autoCadastro(@RequestBody @Valid UsuarioCadastroRequestDTO dto);
}
