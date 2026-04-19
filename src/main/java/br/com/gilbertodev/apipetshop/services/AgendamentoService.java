package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.repositories.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PetService petService;
    private final ServicoService servicoService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, PetService petService, ServicoService servicoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.petService = petService;
        this.servicoService = servicoService;
    }

    @Transactional
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {
        var pet = petService.buscarEntidadePorId(dto.getPetId());
        var servico = servicoService.buscarEntidadePorId(dto.getServicoId());

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setPet(pet);
        agendamento.setServico(servico);
        agendamento.setObservacoes(dto.getObservacoes());
        agendamento = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDTO(agendamento);
    }
}
