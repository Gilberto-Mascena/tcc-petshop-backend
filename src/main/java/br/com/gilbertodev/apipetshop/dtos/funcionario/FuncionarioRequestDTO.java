package br.com.gilbertodev.apipetshop.dtos.funcionario;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Set;

public record FuncionarioRequestDTO(

        @NotBlank(message = "O nome do funcionário é obrigatório")
        String nome,

        @NotBlank(message = "O email do funcionário é obrigatório")
        @Email(message = "O email do funcionário deve ser válido")
        String email,

        @NotBlank(message = "O CPF do funcionário é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF do funcionário deve conter exatamente 11 caracteres")
        String cpf,

        String telefone,

        @NotBlank(message = "O celular do funcionário é obrigatório")
        @Size(min = 10, max = 11, message = "O celular do funcionário deve conter entre 10 e 11 caracteres")
        String celular,

        @NotBlank(message = "O login do funcionário é obrigatório")
        String login,

        @NotBlank(message = "A senha do funcionário é obrigatória")
        @Size(min = 8, message = "A senha do funcionário deve conter no mínimo 8 caracteres")
        String password,

        @Valid
        @NotNull(message = "O endereço do funcionário é obrigatório")
        EnderecoRequestDTO endereco,

        @NotEmpty(message = "O funcionário deve ter pelo menos um perfil de acesso (Role)")
        Set<String> roleNames
) {
}
