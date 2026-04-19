package br.com.gilbertodev.apipetshop.dtos.endereco;

import br.com.gilbertodev.apipetshop.entities.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoResponseDTO {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public EnderecoResponseDTO(Endereco endereco) {
        if (endereco != null) {
            this.logradouro = endereco.getLogradouro();
            this.numero = endereco.getNumero();
            this.complemento = endereco.getComplemento();
            this.bairro = endereco.getBairro();
            this.cidade = endereco.getCidade();
            this.uf = endereco.getUf();
            this.cep = endereco.getCep();
        }
    }
}