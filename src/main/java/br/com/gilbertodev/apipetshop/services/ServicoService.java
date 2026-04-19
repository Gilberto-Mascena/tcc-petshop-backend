package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.messages.ServicoMessages;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.repositories.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO dto) {
        Servico entidade = dto.toEntity();
        return new ServicoResponseDTO(servicoRepository.save(entidade));
    }

    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .map(ServicoResponseDTO::new)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico entidade = servicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));

        entidade.setTipo(dto.getTipo());
        entidade.setValorBase(dto.getValorBase());
        entidade.setObservacoes(dto.getObservacoes());

        return new ServicoResponseDTO(servicoRepository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO);
        }
        servicoRepository.deleteById(id);
    }
}