package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.servico.ServicoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.servico.ServicoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.messages.PetMessages;
import br.com.gilbertodev.apipetshop.enums.messages.ServicoMessages;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.repositories.PetRepository;
import br.com.gilbertodev.apipetshop.repositories.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final PetRepository petRepository;

    public ServicoService(ServicoRepository servicoRepository, PetRepository petRepository) {
        this.servicoRepository = servicoRepository;
        this.petRepository = petRepository;
    }

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO dto) {

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO));

        Servico entidade = dto.toEntity();
        entidade.setPet(pet);

        entidade.calcularValorFinal();

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
        Servico entidade = findOrThrow(id);
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));

        entidade.setTipo(dto.getTipo());
        entidade.setDataHora(dto.getDataHora());
        entidade.setObservacoes(dto.getObservacoes());
        entidade.setPet(pet);

        entidade.calcularValorFinal();

        return new ServicoResponseDTO(servicoRepository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO);
        }
        servicoRepository.deleteById(id);
    }

    private Servico findOrThrow(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ServicoMessages.SERVICO_NAO_ENCONTRADO));
    }
}
