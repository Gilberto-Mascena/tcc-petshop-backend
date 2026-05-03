package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoRequestDTO;
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

            tutor.setEndereco(new Endereco());
            mapearEndereco(tutorRequestDTO.endereco(), tutor.getEndereco());
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

    public void atualizarDados(TutorRequestDTO dto, Tutor tutor) {
        if (dto == null || tutor == null) return;

        if (dto.nome() != null) tutor.setNome(dto.nome());
        if (dto.email() != null) tutor.setEmail(dto.email());
        if (dto.celular() != null) tutor.setCelular(dto.celular());

        mapearEndereco(dto.endereco(), tutor.getEndereco());
    }

    private void mapearEndereco(EnderecoRequestDTO dto, Endereco endereco) {
        if (dto == null || endereco == null) return;

        if (dto.uf() != null) endereco.setUf(dto.uf());
        if (dto.cidade() != null) endereco.setCidade(dto.cidade());
        if (dto.bairro() != null) endereco.setBairro(dto.bairro());
        if (dto.logradouro() != null) endereco.setLogradouro(dto.logradouro());
        if (dto.numero() != null) endereco.setNumero(dto.numero());
        if (dto.complemento() != null) endereco.setComplemento(dto.complemento());
        if (dto.cep() != null) endereco.setCep(dto.cep());
    }
}
