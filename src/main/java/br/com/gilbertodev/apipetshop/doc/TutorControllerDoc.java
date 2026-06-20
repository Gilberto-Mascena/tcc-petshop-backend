package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
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
        name = "Tutores",
        description = "Endpoints para gerenciamento de tutores")
public interface TutorControllerDoc {

    @Operation(
            summary = "Criar um novo tutor",
            description = "Cria um novo tutor com as informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tutor criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos.")
    })
    ResponseEntity<TutorResponseDTO> salvar(@RequestBody @Valid TutorRequestDTO dto);

    @Operation(
            summary = "Listar todos os tutores",
            description = "Retorna uma lista paginada de todos os tutores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tutores retornada com sucesso")
    })
    ResponseEntity<Page<TutorResponseDTO>> listarTodos(Pageable paginacao);

    @Operation(
            summary = "Buscar tutor por ID",
            description = "Retorna os detalhes de um tutor específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    ResponseEntity<TutorResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca global de tutores",
            description = "Realiza uma busca global por tutores com base em um termo fornecido, que pode corresponder ao nome, CPF ou e-mail do tutor. Retorna uma lista paginada de tutores que correspondem ao termo de busca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, retornando os tutores correspondentes"),
            @ApiResponse(responseCode = "422", description = "Termo de busca inválido ou muito curto")
    })
    ResponseEntity<Page<TutorResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, Pageable paginacao);

    @Operation(
            summary = "Atualizar tutor",
            description = "Atualiza as informações de um tutor existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    ResponseEntity<TutorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TutorRequestDTO dto);

    @Operation(
            summary = "Deletar tutor",
            description = "Remove um tutor do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tutor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado para deleção")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
