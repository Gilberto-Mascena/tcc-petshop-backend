package br.com.gilbertodev.apipetshop.dtos.pet;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResumoDTO;
import br.com.gilbertodev.apipetshop.enums.PortePet;

import java.time.LocalDate;

public record PetResponseDTO(
        Long id,
        String nome,
        String especie,
        String raca,
        Integer idade,
        LocalDate dataNascimento,
        String observacoes,
        PortePet porte,
        Long tutorId,
        String nomeTutor,
        TutorResumoDTO tutorResumo
) {
}