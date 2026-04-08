package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoResponseDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.repositories.TutorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Transactional
    public TutorResponseDTO salvar(TutorRequestDTO tutorRequestDTO) {
        Tutor tutor = tutorRequestDTO.toEntity();
        return toTutorResponseDTO(tutorRepository.save(tutor));
    }

    @Transactional(readOnly = true)
    public List<TutorResponseDTO> listarTodos() {
        return tutorRepository.findAll()
                .stream()
                .map(this::toTutorResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TutorResponseDTO buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .map(this::toTutorResponseDTO)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
    }

    @Transactional
    public TutorResponseDTO atualizar(Long id, TutorRequestDTO tutorAtualizado) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        tutor.atualizarDados(tutorAtualizado);
        return toTutorResponseDTO(tutorRepository.save(tutor));
    }

    @Transactional
    public void deletar(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new RuntimeException("Tutor não encontrado");
        }
        tutorRepository.deleteById(id);
    }

    private TutorResponseDTO toTutorResponseDTO(Tutor tutor) {
        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getNome(),
                tutor.getEmail(),
                tutor.getCelular(),
                toEnderecoResponseDTO(tutor.getEndereco())
        );
    }

    private EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoResponseDTO(
                endereco.getUf(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }
}
