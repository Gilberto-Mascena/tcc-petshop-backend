package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.PetResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetResponseDTO salvar(PetRequestDTO petRequestDTO) {
        if (petRequestDTO.getDataNascimento().isBefore(LocalDate.now().minusYears(30))) {
            throw new RuntimeException("Data de nascimento inválida: o pet não pode ter mais de 30 anos.");
        }
        Pet pet = petRequestDTO.toEntity();
        return toPetResponseDTO(petRepository.save(pet));
    }

    public PetResponseDTO atualizar(Long id, PetRequestDTO petAtualizado) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setNome(petAtualizado.getNome());
                    pet.setEspecie(petAtualizado.getEspecie());
                    pet.setRaca(petAtualizado.getRaca());
                    pet.setDataNascimento(petAtualizado.getDataNascimento());
                    pet.setObservacoes(petAtualizado.getObservacoes());
                    return toPetResponseDTO(petRepository.save(pet));
                })
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));
    }

    public List<PetResponseDTO> listarTodos() {
        return petRepository.findAll()
                .stream()
                .map(this::toPetResponseDTO)
                .toList();
    }

    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));
        return toPetResponseDTO(pet);
    }

    public void deletar(Long id) {
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
