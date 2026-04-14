package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.enums.messages.PetMessages;
import br.com.gilbertodev.apipetshop.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public PetResponseDTO salvar(PetRequestDTO petRequestDTO) {
        if (petRequestDTO.getDataNascimento().isBefore(LocalDate.now().minusYears(30))) {
            throw new BusinessException(
                    PetMessages.PET_IDADE_INVALIDA
            );
        }
        Pet pet = petRequestDTO.toEntity();
        return toPetResponseDTO(petRepository.save(pet));
    }

    @Transactional
    public PetResponseDTO atualizar(Long id, PetRequestDTO petAtualizado) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        PetMessages.PET_NAO_ENCONTRADO));
        pet.atualizarDados(petAtualizado);
        return toPetResponseDTO(petRepository.save(pet));
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> listarTodos() {
        return petRepository.findAll()
                .stream()
                .map(this::toPetResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        PetMessages.PET_NAO_ENCONTRADO));
        return toPetResponseDTO(pet);
    }

    @Transactional
    public void deletar(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ObjectNotFoundException(
                    PetMessages.PET_NAO_ENCONTRADO);
        }
        petRepository.deleteById(id);
    }

    private PetResponseDTO toPetResponseDTO(Pet pet) {
        Integer idadeCalculada = null;
        if (pet.getDataNascimento() != null) {
            idadeCalculada = Period.between(pet.getDataNascimento(), LocalDate.now()).getYears();
        }
        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                idadeCalculada,
                pet.getObservacoes(),
                pet.getDataCriacao()
        );
    }
}
