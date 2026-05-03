package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo pet", description = "Cria um novo pet com as informações fornecidas no corpo da requisição.")
    @ApiResponse(responseCode = "201", description = "Pet criado com sucesso")
    @ApiResponse(responseCode = "422", description = "Dados inválidos.")
    public ResponseEntity<PetResponseDTO> salvar(@RequestBody @Valid PetRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pets", description = "Retorna uma lista paginada de todos os pets cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de pets retornada com sucesso")
    public ResponseEntity<Page<PetResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(petService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID", description = "Retorna os detalhes de um pet específico com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Pet encontrado e retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet", description = "Atualiza as informações de um pet existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.")
    @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public ResponseEntity<PetResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PetRequestDTO dto) {
        return ResponseEntity.ok(petService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pet", description = "Remove um pet do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Pet deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado para deleção")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}