package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.ServicoControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.services.ServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController implements ServicoControllerDoc {

    private final ServicoService serviceService;

    public ServicoController(ServicoService serviceService) {
        this.serviceService = serviceService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ServicoResponseDTO> salvar(@Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO response = serviceService.salvar(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<Page<ServicoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"tipo"}) Pageable paginacao) {
        return ResponseEntity.ok(serviceService.listarTodos(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.buscarPorId(id));
    }

    @Override
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<Page<ServicoResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, @PageableDefault(size = 10, sort = {"tipo"}) Pageable paginacao) {
        return ResponseEntity.ok(serviceService.buscaGlobal(termo, paginacao));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.ok(serviceService.atualizar(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        serviceService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
