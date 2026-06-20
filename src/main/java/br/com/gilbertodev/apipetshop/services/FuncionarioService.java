package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Funcionario;
import br.com.gilbertodev.apipetshop.entities.Role;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ObjectNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.FuncionarioMapper;
import br.com.gilbertodev.apipetshop.messages.FuncionarioMessages;
import br.com.gilbertodev.apipetshop.repositories.FuncionarioRepository;
import br.com.gilbertodev.apipetshop.repositories.RoleRepository;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@SuppressWarnings("DuplicatedCode")
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, RoleRepository roleRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.funcionarioMapper = funcionarioMapper;
    }

    @Transactional
    public FuncionarioResponseDTO salvar(FuncionarioRequestDTO dto) {

        if (funcionarioRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException(FuncionarioMessages.CPF_JA_CADASTRADO);
        }
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException(FuncionarioMessages.EMAIL_JA_CADASTRADO);
        }
        if (usuarioRepository.existsByLogin(dto.login())) {
            throw new BusinessException(FuncionarioMessages.LOGIN_JA_CADASTRADO);
        }

        Funcionario funcionario = funcionarioMapper.toEntity(dto);
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.password()));

        Set<Role> rolesVinculadas = new HashSet<>();
        for (String roleName : dto.roleNames()) {
            Role role = roleRepository.findByNome(roleName)
                    .orElseThrow(() -> new ObjectNotFoundException(FuncionarioMessages.ROLE_NAO_ENCONTRADA));
            rolesVinculadas.add(role);
        }
        usuario.setRoles(rolesVinculadas);
        funcionario.setUsuario(usuario);
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDTO(funcionarioSalvo);
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> listarTodos(Pageable paginacao) {
        return funcionarioRepository.findAll(paginacao)
                .map(funcionarioMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = buscarEntidadePorId(id);
        return funcionarioMapper.toResponseDTO(funcionario);
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> buscaGlobal(String termo, Pageable paginacao) {
        if (termo == null || termo.isBlank()) {
            return Page.empty(paginacao);
        }

        String termoLimpo = termo.trim();
        if (termoLimpo.length() < 3) {
            throw new BusinessException(FuncionarioMessages.TERMO_BUSCA_CURTO);
        }

        return funcionarioRepository.buscaGlobal(termoLimpo, paginacao)
                .map(funcionarioMapper::toResponseDTO);
    }

    @Transactional
    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO dto) {

        Funcionario funcionario = buscarEntidadePorId(id);

        if (!funcionario.getCpf().equals(dto.cpf()) && funcionarioRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException(FuncionarioMessages.CPF_JA_CADASTRADO);
        }
        if (!funcionario.getEmail().equals(dto.email()) && funcionarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException(FuncionarioMessages.EMAIL_JA_CADASTRADO);
        }
        if (!funcionario.getUsuario().getLogin().equals(dto.login()) && usuarioRepository.existsByLogin(dto.login())) {
            throw new BusinessException(FuncionarioMessages.LOGIN_JA_CADASTRADO);
        }

        funcionarioMapper.atualizarDados(dto, funcionario);

        Usuario usuario = funcionario.getUsuario();
        usuario.setLogin(dto.login());

        if (dto.password() != null && !dto.password().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.password()));
        }

        Set<Role> rolesVinculadas = new HashSet<>();
        for (String roleName : dto.roleNames()) {
            Role role = roleRepository.findByNome(roleName)
                    .orElseThrow(() -> new ObjectNotFoundException(FuncionarioMessages.ROLE_NAO_ENCONTRADA));
            rolesVinculadas.add(role);
        }
        usuario.setRoles(rolesVinculadas);

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        return funcionarioMapper.toResponseDTO(funcionarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Funcionario funcionario = buscarEntidadePorId(id);
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario buscarEntidadePorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(FuncionarioMessages.FUNCIONARIO_NAO_ENCONTRADO));
    }
}
