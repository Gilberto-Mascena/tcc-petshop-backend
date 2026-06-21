package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.doc.FuncionarioControllerDoc;
import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioResponseDTO;
import br.com.gilbertodev.apipetshop.services.FuncionarioService;
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
@RequestMapping("/api/admin/funcionarios")
public class FuncionarioController implements FuncionarioControllerDoc {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponseDTO> salvar(@RequestBody @Valid FuncionarioRequestDTO dto) {
        FuncionarioResponseDTO response = funcionarioService.salvar(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<FuncionarioResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        Page<FuncionarioResponseDTO> pagina = funcionarioService.listarTodos(paginacao);
        return ResponseEntity.ok(pagina);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id) {
        FuncionarioResponseDTO response = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscaGlobal(@RequestParam String termo, @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        Page<FuncionarioResponseDTO> pagina = funcionarioService.buscaGlobal(termo, paginacao);
        return ResponseEntity.ok(pagina);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioRequestDTO dto) {
        FuncionarioResponseDTO response = funcionarioService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
