package br.com.gilbertodev.apipetshop.dtos.tutor;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TutorRequestDTO(

        @NotBlank(message = "O nome do tutor é obrigatório")
        @Size(min = 2, max = 100, message = "O nome do tutor deve conter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "O email do tutor é obrigatório")
        @Email(message = "O email do tutor deve ser válido")
        String email,

        @NotBlank(message = "O CPF do tutor é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF do tutor deve conter exatamente 11 caracteres")
        String cpf,


        String telefone,

        @NotBlank(message = "O celular do tutor é obrigatório")
        @Size(min = 10, max = 11, message = "O celular do tutor deve conter entre 10 e 11 caracteres")
        String celular,

        @Valid
        @NotNull(message = "O endereço do tutor é obrigatório")
        EnderecoRequestDTO endereco
) {
}
