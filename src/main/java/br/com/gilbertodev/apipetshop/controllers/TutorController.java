package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.services.TutorService;
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
@RequestMapping("/api/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo tutor", description = "Cria um novo tutor com as informações fornecidas no corpo da requisição.")
    @ApiResponse(responseCode = "201", description = "Tutor criado com sucesso")
    @ApiResponse(responseCode = "422", description = "Dados inválidos.")
    public ResponseEntity<TutorResponseDTO> salvar(@RequestBody @Valid TutorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os tutores", description = "Retorna uma lista paginada de todos os tutores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de tutores retornada com sucesso")
    public ResponseEntity<Page<TutorResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(tutorService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tutor por ID", description = "Retorna os detalhes de um tutor específico com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Tutor encontrado e retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    public ResponseEntity<TutorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tutor", description = "Atualiza as informações de um tutor existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.")
    @ApiResponse(responseCode = "200", description = "Tutor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    public ResponseEntity<TutorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TutorRequestDTO dto) {
        return ResponseEntity.ok(tutorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tutor", description = "Remove um tutor do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Tutor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Tutor não encontrado para deleção")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}