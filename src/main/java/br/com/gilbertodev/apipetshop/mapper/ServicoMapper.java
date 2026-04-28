package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Servico;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper {

    public Servico toEntity(ServicoRequestDTO servicoRequestDTO) {
        if (servicoRequestDTO == null) return null;

        Servico servico = new Servico();
        servico.setTipo(servicoRequestDTO.tipo());
        servico.setObservacoes(servicoRequestDTO.observacoes());
        servico.setValorBase(servicoRequestDTO.valorBase());

        return servico;
    }

    public ServicoResponseDTO toResponseDTO(Servico servico) {
        if (servico == null) return null;

        return new ServicoResponseDTO(
                servico.getId(),
                servico.getTipo(),
                servico.getObservacoes(),
                servico.getValorBase()
        );
    }

    public void atualizarDados(ServicoRequestDTO dto, Servico servico) {

        if (dto == null || servico == null) return;

        if (dto.tipo() != null) {
            servico.setTipo(dto.tipo());
        }
        if (dto.observacoes() != null) {
            servico.setObservacoes(dto.observacoes());
        }
        if (dto.valorBase() != null) {
            servico.setValorBase(dto.valorBase());
        }
    }
}
