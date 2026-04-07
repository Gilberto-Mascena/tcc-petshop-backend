package br.com.gilbertodev.apipetshop.dtos.tutor;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TutorResponseDTO {

    private final Long id;
    private final String nome;
    private final String email;
    private final String celular;
    private final EnderecoResponseDTO endereco;
}
