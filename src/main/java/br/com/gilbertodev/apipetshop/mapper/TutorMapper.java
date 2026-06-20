package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("DuplicatedCode")
public class TutorMapper {

    private final EnderecoMapper enderecoMapper;

    public TutorMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

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
            enderecoMapper.mapearEndereco(tutorRequestDTO.endereco(), tutor.getEndereco());
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

        if (dto.endereco() != null) {
            if (tutor.getEndereco() == null) {
                tutor.setEndereco(new Endereco());
            }
            enderecoMapper.mapearEndereco(dto.endereco(), tutor.getEndereco());
        }
    }
}
