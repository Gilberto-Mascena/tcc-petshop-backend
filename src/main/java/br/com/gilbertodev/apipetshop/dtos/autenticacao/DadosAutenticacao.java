package br.com.gilbertodev.apipetshop.dtos.autenticacao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosAutenticacao {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
