package br.com.gilbertodev.apipetshop.controllers;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService service) {
        this.agendamentoService = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.salvar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"dataHora"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(agendamentoService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusAgendamento novoStatus) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, novoStatus));
    }
}