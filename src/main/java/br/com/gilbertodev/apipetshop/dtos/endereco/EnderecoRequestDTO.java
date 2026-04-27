package br.com.gilbertodev.apipetshop.dtos.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(

        @NotBlank(message = "A UF é obrigatória")
        @Size(min = 2, max = 2, message = "A UF deve conter exatamente 2 caracteres (ex: RJ)")
        String uf,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O bairro é obrigatório")
        String bairro,

        @NotBlank(message = "O logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "O número é obrigatório")
        String numero,

        String complemento,

        @NotBlank(message = "O CEP é obrigatório")
        String cep
) {
}
