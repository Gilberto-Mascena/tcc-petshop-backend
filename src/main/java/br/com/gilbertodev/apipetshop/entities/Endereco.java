package br.com.gilbertodev.apipetshop.entities;

import jakarta.persistence.Embeddable;
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

    private String estado;
    private String cidade;
    private String logradouro;
    private String numero;
    private String cep;
    private String bairro;
    private String complemento;
}
