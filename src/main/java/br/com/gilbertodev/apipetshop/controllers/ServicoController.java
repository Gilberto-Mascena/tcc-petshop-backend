package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.services.ServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> salvar(@Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO response = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ServicoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"tipo"}) Pageable paginacao) {
        return ResponseEntity.ok(service.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
