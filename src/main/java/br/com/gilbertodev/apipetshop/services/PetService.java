package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.enums.messages.PetMessages;
import br.com.gilbertodev.apipetshop.enums.messages.TutorMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.repositories.PetRepository;
import br.com.gilbertodev.apipetshop.repositories.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    public PetService(PetRepository petRepository, TutorRepository tutorRepository) {
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
    }

    @Transactional
    public PetResponseDTO salvar(PetRequestDTO petRequestDTO) {
        if (petRequestDTO.getDataNascimento().isBefore(LocalDate.now().minusYears(30))) {
            throw new BusinessException(PetMessages.PET_IDADE_INVALIDA);
        }

        Pet pet = petRequestDTO.toEntity();

        Tutor tutor = tutorRepository.findById(petRequestDTO.getTutorId())
                .orElseThrow(() -> new BusinessException(TutorMessages.TUTOR_NAO_ENCONTRADO));

        pet.setTutor(tutor);
        return new PetResponseDTO(petRepository.save(pet));
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> listarTodos() {
        return petRepository.findAll()
                .stream()
                .map(PetResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetResponseDTO buscarPorId(Long id) {
        return petRepository.findById(id)
                .map(PetResponseDTO::new)
                .orElseThrow(() -> new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO));
    }

    @Transactional
    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO));

        pet.atualizarDados(dto);

        if (dto.getTutorId() != null && !dto.getTutorId().equals(pet.getTutor().getId())) {
            Tutor novoTutor = tutorRepository.findById(dto.getTutorId())
                    .orElseThrow(() -> new BusinessException(TutorMessages.TUTOR_NAO_ENCONTRADO));
            pet.setTutor(novoTutor);
        }

        return new PetResponseDTO(petRepository.save(pet));
    }

    @Transactional
    public void deletar(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO);
        }
        petRepository.deleteById(id);
    }

    public Pet buscarEntidadePorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));
    }
}