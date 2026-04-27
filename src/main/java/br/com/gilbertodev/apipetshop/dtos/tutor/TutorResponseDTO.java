package br.com.gilbertodev.apipetshop.dtos.tutor;

public record TutorResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone,
        String celular
) {
}
