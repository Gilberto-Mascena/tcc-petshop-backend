package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco {

    @Column(name = "uf", length = 2)
    private String uf;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;

    public void atualizarDados(@Valid @NotNull(message = "O endereço do tutor é obrigatório") EnderecoRequestDTO endereco) {
        this.uf = endereco.uf();
        this.cidade = endereco.cidade();
        this.bairro = endereco.bairro();
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cep = endereco.cep();
    }
}
