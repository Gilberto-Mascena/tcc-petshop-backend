package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.pet.PetResponseDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResumoDTO;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public Pet toEntity(PetRequestDTO dto, Tutor tutor) {
        if (dto == null) return null;

        Pet pet = new Pet();
        pet.setNome(dto.nome());
        pet.setEspecie(dto.especie());
        pet.setRaca(dto.raca());
        pet.setDataNascimento(dto.dataNascimento());
        pet.setObservacoes(dto.observacoes());
        pet.setPorte(dto.porte());
        pet.setTutor(tutor);

        return pet;
    }

    public PetResponseDTO toResponseDTO(Pet pet) {
        if (pet == null) return null;

        Tutor tutor = pet.getTutor();
        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getIdade(),
                pet.getDataNascimento(),
                pet.getObservacoes(),
                pet.getPorte(),
                tutor != null ? tutor.getId() : null,
                tutor != null ? tutor.getNome() : null,
                tutor != null ? new TutorResumoDTO(tutor) : null
        );
    }

    public void updateEntityFromDTO(PetRequestDTO dto, Pet pet) {
        if (dto == null || pet == null) return;

        if (dto.nome() != null) pet.setNome(dto.nome());
        if (dto.especie() != null) pet.setEspecie(dto.especie());
        if (dto.raca() != null) pet.setRaca(dto.raca());
        if (dto.dataNascimento() != null) pet.setDataNascimento(dto.dataNascimento());
        if (dto.observacoes() != null) pet.setObservacoes(dto.observacoes());
        if (dto.porte() != null) pet.setPorte(dto.porte());
    }
}