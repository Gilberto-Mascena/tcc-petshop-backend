package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.messages.ServicoMessages;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.ServicoMapper;
import br.com.gilbertodev.apipetshop.repositories.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO servicoRequestDTO) {
        Servico servico = servicoMapper.toEntity(servicoRequestDTO);
        Servico servicoSalvo = servicoRepository.save(servico);
        return servicoMapper.toResponseDTO(servicoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(servicoMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .map(servicoMapper::toResponseDTO)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO servicoAtualizado) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));
        servicoMapper.atualizarDados(servicoAtualizado, servico);
        return servicoMapper.toResponseDTO(servicoRepository.save(servico));
    }

    @Transactional
    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO);
        }
        servicoRepository.deleteById(id);
    }

    public Servico buscarEntidadePorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));
    }
}