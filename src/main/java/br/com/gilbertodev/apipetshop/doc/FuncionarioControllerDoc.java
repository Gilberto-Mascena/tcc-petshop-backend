package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioResponseDTO;
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

@Tag(
        name = "Funcionário",
        description = "Endpoints para gerenciamento de funcionários")
public interface FuncionarioControllerDoc {

    @Operation(
            summary = "Cadastrar um novo funcionário",
            description = "Cria um novo funcionário no sistema com perfil de acesso específico. A senha fornecida será criptografada automaticamente antes do armazenamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos enviados"),
            @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos")
    })
    ResponseEntity<FuncionarioResponseDTO> salvar(@RequestBody @Valid FuncionarioRequestDTO dto);

    @Operation(
            summary = "Listar todos os funcionários",
            description = "Retorna uma lista paginada com todos os funcionários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso")
    })
    ResponseEntity<Page<FuncionarioResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = "nome") Pageable paginacao);

    @Operation(
            summary = "Buscar funcionário por ID",
            description = "Retorna os detalhes de um funcionário específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca global de funcionários",
            description = "Permite buscar funcionários por login ou outros critérios, retornando uma lista paginada de resultados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, retornando os funcionários correspondentes")
    })
    ResponseEntity<Page<FuncionarioResponseDTO>> buscaGlobal(@RequestParam String termo, @PageableDefault(size = 10, sort = "nome") Pageable paginacao);

    @Operation(
            summary = "Atualizar funcionário",
            description = "Atualiza os dados de um funcionário existente, incluindo a possibilidade de alterar a senha, que será criptografada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioRequestDTO dto);

    @Operation(
            summary = "Deletar funcionário",
            description = "Remove um funcionário do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado para deleção")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
