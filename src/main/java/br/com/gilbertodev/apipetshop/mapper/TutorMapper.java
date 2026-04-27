package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import org.springframework.stereotype.Component;

@Component
public class TutorMapper {

    public Tutor toEntity(TutorRequestDTO tutorRequestDTO) {
        if (tutorRequestDTO == null) return null;

        Tutor tutor = new Tutor();
        tutor.setNome(tutorRequestDTO.nome());
        tutor.setEmail(tutorRequestDTO.email());
        tutor.setCpf(tutorRequestDTO.cpf());
        tutor.setTelefone(tutorRequestDTO.telefone());
        tutor.setCelular(tutorRequestDTO.celular());

        if (tutorRequestDTO.endereco() != null) {
            Endereco end = new Endereco();
            end.setUf(tutorRequestDTO.endereco().uf());
            end.setCidade(tutorRequestDTO.endereco().cidade());
            end.setBairro(tutorRequestDTO.endereco().bairro());
            end.setLogradouro(tutorRequestDTO.endereco().logradouro());
            end.setNumero(tutorRequestDTO.endereco().numero());
            end.setComplemento(tutorRequestDTO.endereco().complemento());
            end.setCep(tutorRequestDTO.endereco().cep());
            tutor.setEndereco(end);
        }

        return tutor;
    }

    public TutorResponseDTO toResponseDTO(Tutor tutor) {
        if (tutor == null) return null;

        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getNome(),
                tutor.getEmail(),
                tutor.getTelefone(),
                tutor.getCelular()
        );
    }
}
