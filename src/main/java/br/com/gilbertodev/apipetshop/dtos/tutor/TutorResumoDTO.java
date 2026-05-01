package br.com.gilbertodev.apipetshop.dtos.tutor;

import br.com.gilbertodev.apipetshop.entities.Tutor;

public record TutorResumoDTO(
        Long id, String nome, String email
) {

    public TutorResumoDTO(Tutor tutor) {
        this(tutor.getId(), tutor.getNome(), tutor.getEmail());
    }
}
