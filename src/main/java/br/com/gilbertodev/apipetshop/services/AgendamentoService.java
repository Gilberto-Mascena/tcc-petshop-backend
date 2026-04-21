package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.PortePet;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.enums.messages.AgendamentoMessages;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.repositories.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoService servicoService;
    private final PetService petService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ServicoService servicoService, PetService petService) {
        this.agendamentoRepository = agendamentoRepository;
        this.servicoService = servicoService;
        this.petService = petService;
    }

    @Transactional
    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {
        Pet pet = petService.buscarEntidadePorId(dto.getPetId());
        Servico servico = servicoService.buscarEntidadePorId(dto.getServicoId());

        Agendamento agendamento = new Agendamento();
        agendamento.setPet(pet);
        agendamento.setServico(servico);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacoes(dto.getObservacoes());
        agendamento.setStatus(StatusAgendamento.PENDENTE);

        BigDecimal valorCalculado = calcularValorComAcrescimo(servico.getValorBase(), pet.getPorte());
        agendamento.setValorTotal(valorCalculado);

        agendamento = agendamentoRepository.save(agendamento);
        return new AgendamentoResponseDTO(agendamento);
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(AgendamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AgendamentoMessages.AGENDAMENTO_NAO_ENCONTRADO));
        return new AgendamentoResponseDTO(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AgendamentoMessages.AGENDAMENTO_NAO_ENCONTRADO));

        agendamento.setStatus(novoStatus);
        return new AgendamentoResponseDTO(agendamento);
    }

    private BigDecimal calcularValorComAcrescimo(BigDecimal valorBase, PortePet porte) {
        BigDecimal multiplicador = switch (porte) {
            case MEDIO -> new BigDecimal("1.20");
            case GRANDE -> new BigDecimal("1.50");
            default -> BigDecimal.ONE;
        };
        return valorBase.multiply(multiplicador).setScale(2, RoundingMode.HALF_UP);
    }
}
