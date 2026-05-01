package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.services.TutorService;
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
    public ResponseEntity<TutorResponseDTO> salvar(@RequestBody @Valid TutorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<TutorResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(tutorService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TutorRequestDTO dto) {
        return ResponseEntity.ok(tutorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
