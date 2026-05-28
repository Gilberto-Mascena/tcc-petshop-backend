package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário com senha criptografada")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso"),
    })
    public ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioSalvo = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista paginada de todos os usuários cadastrados.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"login"}) Pageable paginacao) {
        return ResponseEntity.ok(usuarioService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os detalhes de um usuário específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado e retornado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    @Operation(
            summary = "Busca global de usuários",
            description = "Permite buscar usuários por login ou outros critérios, retornando uma lista paginada de resultados.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso, retornando os usuários correspondentes")
    })
    public ResponseEntity<Page<UsuarioResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, @PageableDefault(size = 10, sort = {"login"}) Pageable paginacao) {
        return ResponseEntity.ok(usuarioService.buscaGlobal(termo, paginacao));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente, incluindo a possibilidade de alterar a senha, que será criptografada.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário atualizado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        usuarioService.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado para deleção")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}