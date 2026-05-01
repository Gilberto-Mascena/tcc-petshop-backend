package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.enums.messages.AgendamentoMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.AgendamentoMapper;
import br.com.gilbertodev.apipetshop.repositories.AgendamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoService servicoService;
    private final PetService petService;
    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ServicoService servicoService, PetService petService, AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.servicoService = servicoService;
        this.petService = petService;
        this.agendamentoMapper = agendamentoMapper;
    }

    @Transactional
    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {
        Pet pet = petService.buscarEntidadePorId(dto.petId());
        Servico servico = servicoService.buscarEntidadePorId(dto.servicoId());

        Agendamento agendamento = agendamentoMapper.toEntity(dto, pet, servico);
        agendamento.calcularValorFinal();
        return agendamentoMapper.toResponseDTO(agendamentoRepository.save(agendamento));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> listarTodos(Pageable paginacao) {
        return agendamentoRepository.findAll(paginacao)
                .map(agendamentoMapper::toResponseDTO);

    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamentoMapper::toResponseDTO)
                .orElseThrow(() -> new ObjectNotFoundException(AgendamentoMessages.AGENDAMENTO_NAO_ENCONTRADO));
    }

    @Transactional
    public AgendamentoResponseDTO atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = buscarEntidadePorId(id);

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO) {
            throw new BusinessException(AgendamentoMessages.STATUS_NAO_PODE_SER_ALTERADO);
        }

        agendamento.setStatus(novoStatus);
        return agendamentoMapper.toResponseDTO(agendamentoRepository.save(agendamento));
    }

    public Agendamento buscarEntidadePorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AgendamentoMessages.AGENDAMENTO_NAO_ENCONTRADO));
    }
}