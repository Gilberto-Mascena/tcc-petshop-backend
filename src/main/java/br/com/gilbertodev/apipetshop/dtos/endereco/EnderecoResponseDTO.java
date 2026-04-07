package br.com.gilbertodev.apipetshop.dtos.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EnderecoResponseDTO {

    private final String uf;
    private final String cidade;
    private final String bairro;
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String cep;
}
