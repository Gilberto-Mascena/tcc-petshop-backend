package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.tutor.TutorResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.enums.messages.TutorMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.TutorMapper;
import br.com.gilbertodev.apipetshop.repositories.TutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper;

    public TutorService(TutorRepository tutorRepository, TutorMapper tutorMapper) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
    }

    @Transactional
    public TutorResponseDTO salvar(TutorRequestDTO tutorRequestDTO) {

        if (tutorRepository.existsByCpf(tutorRequestDTO.cpf())) {
            throw new BusinessException(TutorMessages.CPF_JA_CADASTRADO);
        }
        Tutor tutor = tutorMapper.toEntity(tutorRequestDTO);
        Tutor tutorSalvo = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorSalvo);
    }

    @Transactional(readOnly = true)
    public Page<TutorResponseDTO> listarTodos(Pageable paginacao) {
        return tutorRepository.findAll(paginacao).map(tutorMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public TutorResponseDTO buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .map(tutorMapper::toResponseDTO)
                .orElseThrow(() -> new ObjectNotFoundException(
                        TutorMessages.TUTOR_NAO_ENCONTRADO));
    }

    @Transactional
    public TutorResponseDTO atualizar(Long id, TutorRequestDTO tutorAtualizado) {
        Tutor tutor = buscarEntidadePorId(id);

        if (!tutorAtualizado.cpf().equals(tutor.getCpf())) {
            if (tutorRepository.existsByCpf(tutorAtualizado.cpf())) {
                throw new BusinessException(TutorMessages.CPF_JA_CADASTRADO);
            }
        }
        tutor.atualizarDados(tutorAtualizado);
        Tutor tutorSalvo = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorSalvo);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = buscarEntidadePorId(id);
        tutorRepository.deleteById(tutor.getId());
    }

    public Tutor buscarEntidadePorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(TutorMessages.TUTOR_NAO_ENCONTRADO));
    }
}
