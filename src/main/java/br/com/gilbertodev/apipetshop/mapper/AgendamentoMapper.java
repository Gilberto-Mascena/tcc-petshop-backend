package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDTO dto, Pet pet, Servico servico) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.dataHora());
        agendamento.setPet(pet);
        agendamento.setServico(servico);
        agendamento.setObservacoes(dto.observacoes());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamento;
    }

    public AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        if (agendamento == null) return null;

        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getStatus(),
                agendamento.getPet().getNome(),
                agendamento.getPet().getTutor().getNome(),
                agendamento.getServico().getTipo(),
                agendamento.getObservacoes(),
                agendamento.getValorTotal()
        );
    }
}