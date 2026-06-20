package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema")
public interface UsuarioControllerDoc {

    @Operation(summary = "Criar um novo usuário avulso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos enviados"),
            @ApiResponse(responseCode = "422", description = "Login já cadastrado no sistema")
    })
    ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody @Valid UsuarioRequestDTO dto);

    @Operation(summary = "Listar todos os usuários com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso")
    })
    ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = "login") Pageable paginacao);

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Busca global de usuários por login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, retornando os usuários correspondentes")
    })
    ResponseEntity<Page<UsuarioResponseDTO>> buscaGlobal(@RequestParam String termo, @PageableDefault(size = 10, sort = "login") Pageable paginacao);

    @Operation(summary = "Atualizar um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Login já cadastrado no sistema")
    })
    ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto);

    @Operation(summary = "Deletar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}