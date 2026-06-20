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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(
        name = "Usuários",
        description = "Endpoints para gerenciamento de usuários")
public interface UsuarioControllerDoc {

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Cria um novo usuário no sistema com perfil de acesso específico. A senha fornecida será criptografada automaticamente antes do armazenamento.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso"),
            @ApiResponse(
                    responseCode = "422",
                    description = "Dados de entrada inválidos")
    })
    ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO dto);

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista paginada com todos os usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem realizada com sucesso")
    })
    ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(Pageable paginacao);

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os detalhes de um usuário específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado e retornado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca global de usuários",
            description = "Permite buscar usuários por login ou outros critérios, retornando uma lista paginada de resultados.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso, retornando os usuários correspondentes")
    })
    ResponseEntity<Page<UsuarioResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, Pageable paginacao);

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente, incluindo a possibilidade de alterar a senha, que será criptografada.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário atualizado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado")
    })
    ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto);

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado para deleção")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
