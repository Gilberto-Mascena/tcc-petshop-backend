package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.FuncionarioControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.services.UsuarioService;
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
@RequestMapping("/api/usuarios")
public class FuncionarioController implements FuncionarioControllerDoc {

    private final UsuarioService usuarioService;

    public FuncionarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.salvar(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"login"}) Pageable paginacao) {
        return ResponseEntity.ok(usuarioService.listarTodos(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Override
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Page<UsuarioResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, @PageableDefault(size = 10, sort = {"login"}) Pageable paginacao) {
        return ResponseEntity.ok(usuarioService.buscaGlobal(termo, paginacao));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        usuarioService.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}