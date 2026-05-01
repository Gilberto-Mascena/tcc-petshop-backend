package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.services.PetService;
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
    public ResponseEntity<PetResponseDTO> salvar(@RequestBody @Valid PetRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<PetResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(petService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PetRequestDTO dto) {
        return ResponseEntity.ok(petService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}