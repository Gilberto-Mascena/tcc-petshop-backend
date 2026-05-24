package br.com.gilbertodev.apipetshop.controllers;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Cria um novo agendamento",
            description = "Cria um novo agendamento para um pet, associando-o a um tutor e a um serviço específico. O status inicial do agendamento é definido como AGENDADO.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Agendamento criado com sucesso"),
            @ApiResponse(
                    responseCode = "422",
                    description = "Dados de entrada inválidos")
    })
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.salvar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os agendamentos",
            description = "Retorna uma lista paginada de todos os agendamentos cadastrados no sistema, ordenados por data e hora de forma ascendente.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de agendamentos retornada com sucesso")
    })
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"dataHora"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(agendamentoService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um agendamento por ID",
            description = "Retorna os detalhes de um agendamento específico com base no ID fornecido. Se o agendamento existir, suas informações serão retornadas; caso contrário, uma resposta de erro será gerada.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Agendamento encontrado e retornado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Agendamento não encontrado")
    })
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Busca global de agendamentos",
            description = "Realiza uma busca global por agendamentos com base em um termo de pesquisa fornecido. O termo pode ser parte da data e hora, status, nome do pet, tipo de serviço, valor total ou observações do agendamento. Retorna uma lista paginada de agendamentos que correspondem ao termo de busca.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso, retornando os agendamentos correspondentes"),
            @ApiResponse(
                    responseCode = "422",
                    description = "O termo de busca deve conter pelo menos 3 caracteres")
    })
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, @PageableDefault(size = 10, sort = {"dataHora"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(agendamentoService.buscaGlobal(termo, paginacao));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um agendamento existente",
            description = "Atualiza as informações de um agendamento existente com base no ID fornecido e nos dados fornecidos no corpo da requisição. Permite atualizar a data e hora do agendamento, o serviço associado, o pet e o tutor. O status do agendamento não pode ser atualizado por esta operação.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Agendamento atualizado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Agendamento não encontrado")
    })
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualiza o status de um agendamento",
            description = "Permite atualizar o status de um agendamento específico com base no ID fornecido e no novo status fornecido como parâmetro. O novo status deve ser um valor válido do enum StatusAgendamento. A atualização do status segue regras de negócio específicas, como não permitir a alteração para o mesmo status atual ou para um status inválido.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status atualizado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Agendamento não encontrado"),
            @ApiResponse(
                    responseCode = "422",
                    description = "Status inválido ou regra de negócio violada")
    })
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusAgendamento novoStatus) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, novoStatus));
    }
}