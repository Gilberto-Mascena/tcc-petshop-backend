package br.com.gilbertodev.apipetshop.dtos.autenticacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DadosTokenJWT {

    private String token;
}
