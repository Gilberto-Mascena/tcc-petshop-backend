package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.enums.messages.PetMessages;
import br.com.gilbertodev.apipetshop.enums.messages.TutorMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.PetMapper;
import br.com.gilbertodev.apipetshop.repositories.PetRepository;
import br.com.gilbertodev.apipetshop.repositories.TutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, TutorRepository tutorRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
        this.petMapper = petMapper;
    }

    @Transactional
    public PetResponseDTO salvar(PetRequestDTO petRequestDTO) {

        if (petRequestDTO.dataNascimento() != null &&
                petRequestDTO.dataNascimento().isBefore(LocalDate.now().minusYears(30))) {
            throw new BusinessException(PetMessages.PET_IDADE_INVALIDA);
        }

        Tutor tutor = tutorRepository.findById(petRequestDTO.tutorId())
                .orElseThrow(() -> new ObjectNotFoundException(TutorMessages.TUTOR_NAO_ENCONTRADO));

        Pet pet = petMapper.toEntity(petRequestDTO, tutor);

        return petMapper.toResponseDTO(petRepository.save(pet));
    }

    @Transactional(readOnly = true)
    public Page<PetResponseDTO> listarTodos(Pageable paginacao) {
        return petRepository.findAll(paginacao)
                .map(petMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PetResponseDTO buscarPorId(Long id) {
        return petRepository.findById(id)
                .map(petMapper::toResponseDTO)
                .orElseThrow(() -> new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO));
    }

    @Transactional
    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {
        Pet pet = buscarEntidadePorId(id);
        petMapper.atualizarDados(dto, pet);

        if (dto.tutorId() != null && !dto.tutorId().equals(pet.getTutor().getId())) {
            Tutor novoTutor = tutorRepository.findById(dto.tutorId())
                    .orElseThrow(() -> new ObjectNotFoundException(TutorMessages.TUTOR_NAO_ENCONTRADO));
            pet.setTutor(novoTutor);
        }

        return petMapper.toResponseDTO(petRepository.save(pet));
    }

    @Transactional
    public void deletar(Long id) {
        Pet pet = buscarEntidadePorId(id);
        petRepository.deleteById(pet.getId());
    }

    public Pet buscarEntidadePorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(PetMessages.PET_NAO_ENCONTRADO));
    }
}