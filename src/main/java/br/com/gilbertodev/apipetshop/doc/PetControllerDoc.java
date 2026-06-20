package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
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
        name = "Pets",
        description = "Endpoints para gerenciamento de pets")
public interface PetControllerDoc {

    @Operation(
            summary = "Criar um novo pet",
            description = "Cria um novo pet com as informações fornecidas no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos.")
    })
    ResponseEntity<PetResponseDTO> salvar(@RequestBody @Valid PetRequestDTO dto);

    @Operation(
            summary = "Listar todos os pets",
            description = "Retorna uma lista paginada de todos os pets cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pets retornada com sucesso")
    })
    ResponseEntity<Page<PetResponseDTO>> listarTodos(Pageable paginacao);

    @Operation(
            summary = "Buscar pet por ID",
            description = "Retorna os detalhes de um pet específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca global de pets",
            description = "Realiza uma busca global por pets com base em um termo fornecido, que pode corresponder ao nome, raça, espécie do pet ou nome do tutor. Retorna uma lista paginada de pets que correspondem ao termo de busca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, retornando os pets correspondentes"),
            @ApiResponse(responseCode = "422", description = "Termo de busca inválido ou muito curto")
    })
    ResponseEntity<Page<PetResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, Pageable paginacao);

    @Operation(
            summary = "Atualizar pet",
            description = "Atualiza as informações de um pet existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    ResponseEntity<PetResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PetRequestDTO dto);

    @Operation(
            summary = "Deletar pet",
            description = "Remove um pet do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado para deleção")
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
