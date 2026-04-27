package br.com.gilbertodev.apipetshop.dtos.endereco;

public record EnderecoResponseDTO(

        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
) {
}
