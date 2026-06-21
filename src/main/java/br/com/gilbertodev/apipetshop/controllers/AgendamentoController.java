package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.AgendamentoControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.services.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController implements AgendamentoControllerDoc {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.salvar(dto);
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
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"dataHora"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(agendamentoService.listarTodos(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @Override
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, @PageableDefault(size = 10, sort = {"dataHora"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(agendamentoService.buscaGlobal(termo, paginacao));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    @Override
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusAgendamento novoStatus) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, novoStatus));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}