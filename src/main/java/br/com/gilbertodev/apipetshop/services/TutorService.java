package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.enums.messages.TutorMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
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

        if (tutorRepository.existsByCpf(tutorRequestDTO.getCpf())) {
            throw new BusinessException(TutorMessages.CPF_JA_CADASTRADO);
        }
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
                .orElseThrow(() -> new ObjectNotFoundException(
                        TutorMessages.TUTOR_NAO_ENCONTRADO));
    }

    @Transactional
    public TutorResponseDTO atualizar(Long id, TutorRequestDTO tutorAtualizado) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        TutorMessages.TUTOR_NAO_ENCONTRADO));

        if (!tutorAtualizado.getCpf().equals(tutor.getCpf())) {
            if (tutorRepository.existsByCpf(tutorAtualizado.getCpf())) {
                throw new BusinessException(TutorMessages.CPF_JA_CADASTRADO);
            }
        }
        tutor.atualizarDados(tutorAtualizado);
        return toTutorResponseDTO(tutorRepository.save(tutor));
    }

    @Transactional
    public void deletar(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new ObjectNotFoundException(TutorMessages.TUTOR_NAO_ENCONTRADO);
        }
        tutorRepository.deleteById(id);
    }

    private TutorResponseDTO toTutorResponseDTO(Tutor tutor) {
        return new TutorResponseDTO(tutor);
    }
}
