package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.services.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService service) {
        this.agendamentoService = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
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
