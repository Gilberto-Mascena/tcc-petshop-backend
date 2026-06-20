package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
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
        name = "Serviços",
        description = "Endpoints para gerenciamento de serviços")
public interface ServicoControllerDoc {

    @Operation(
            summary = "Criar um novo serviço",
            description = "Cria um novo serviço com as informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos, verifique as informações fornecidas")
    })
    ResponseEntity<ServicoResponseDTO> salvar(@Valid @RequestBody ServicoRequestDTO dto);

    @Operation(
            summary = "Listar todos os serviços",
            description = "Retorna uma lista paginada de todos os serviços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum serviço encontrado")
    })
    ResponseEntity<Page<ServicoResponseDTO>> listarTodos(Pageable paginacao);

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna os detalhes de um serviço específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca global de serviços",
            description = "Realiza uma busca global por serviços com base em um termo de pesquisa, retornando resultados paginados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, resultados retornados"),
            @ApiResponse(responseCode = "422", description = "Termo de busca inválido, deve conter pelo menos 3 caracteres")
    })
    ResponseEntity<Page<ServicoResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, Pageable paginacao);

    @Operation(
            summary = "Atualizar serviço",
            description = "Atualiza as informações de um serviço existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Serviço não encontrado")
    })
    ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO dto);

    @Operation(
            summary = "Deletar serviço",
            description = "Remove um serviço do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado para deleção")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
