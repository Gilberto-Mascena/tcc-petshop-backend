package br.com.gilbertodev.apipetshop.dtos.funcionario;

public record FuncionarioResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone,
        String celular
) {
}
