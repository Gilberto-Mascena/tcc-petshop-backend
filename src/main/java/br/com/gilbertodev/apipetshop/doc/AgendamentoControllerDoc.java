package br.com.gilbertodev.apipetshop.doc;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(
        name = "Agendamentos",
        description = "Endpoints para gerenciamento de agendamentos")
public interface AgendamentoControllerDoc {

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
    ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO dto);

    @Operation(
            summary = "Lista todos os agendamentos",
            description = "Retorna uma lista paginada de todos os agendamentos cadastrados no sistema, ordenados por data e hora de forma ascendente.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de agendamentos retornada com sucesso")
    })
    ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(Pageable pageable);

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
    ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id);

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
    ResponseEntity<Page<AgendamentoResponseDTO>> buscaGlobal(@RequestParam(required = false) String termo, Pageable pageable);

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
    ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto);

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
    ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusAgendamento novoStatus);
}
