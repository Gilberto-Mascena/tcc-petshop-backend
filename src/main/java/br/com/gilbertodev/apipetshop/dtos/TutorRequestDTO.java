package br.com.gilbertodev.apipetshop.dtos;

import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorRequestDTO {

    @NotBlank(message = "O nome do tutor é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do tutor deve conter entre 2 e 100 caracteres")
    private String nome;
    private String email;

    @NotBlank(message = "O CPF do tutor é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF do tutor deve conter exatamente 11 caracteres")
    private String cpf;
    private String telefone;

    @NotBlank(message = "O celular do tutor é obrigatório")
    @Size(min = 10, max = 11, message = "O celular do tutor deve conter entre 10 e 11 caracteres")
    private String celular;

    @Valid
    @NotNull(message = "O endereço do tutor é obrigatório")
    private EnderecoRequestDTO endereco;

    public Tutor toEntity() {
        Tutor tutor = new Tutor();
        tutor.setNome(this.nome);
        tutor.setEmail(this.email);
        tutor.setCpf(this.cpf);
        tutor.setTelefone(this.telefone);
        tutor.setCelular(this.celular);

        if (this.endereco != null) {
            Endereco endEntity = new Endereco();
            endEntity.setUf(this.endereco.getUf());
            endEntity.setCidade(this.endereco.getCidade());
            endEntity.setBairro(this.endereco.getBairro());
            endEntity.setLogradouro(this.endereco.getLogradouro());
            endEntity.setNumero(this.endereco.getNumero());
            endEntity.setComplemento(this.endereco.getComplemento());
            endEntity.setCep(this.endereco.getCep());
            tutor.setEndereco(endEntity);
        }
        return tutor;
    }
}
