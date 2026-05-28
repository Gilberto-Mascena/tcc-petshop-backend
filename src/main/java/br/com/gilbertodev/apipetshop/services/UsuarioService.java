package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Role;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.exceptions.ResourceNotFoundException;
import br.com.gilbertodev.apipetshop.mapper.UsuarioMapper;
import br.com.gilbertodev.apipetshop.messages.ResourceMessages;
import br.com.gilbertodev.apipetshop.messages.UsuarioMessages;
import br.com.gilbertodev.apipetshop.repositories.RoleRepository;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (userRepository.existsByLogin(dto.login())) {
            throw new BusinessException(UsuarioMessages.LOGIN_JA_CADASTRADO);
        }

        Usuario novoUsuario = usuarioMapper.toEntity(dto);
        novoUsuario.setSenha(passwordEncoder.encode(dto.password()));

        Role rolePadrao = roleRepository.findByNome("ROLE_ATENDENTE")
                .orElseThrow(() -> new ResourceNotFoundException(ResourceMessages.ROLE_PADRAO_NAO_ENCONTRADA));
        novoUsuario.getRoles().add(rolePadrao);

        userRepository.save(novoUsuario);

        return usuarioMapper.toResponseDTO(novoUsuario);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listarTodos(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return userRepository.findById(id)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException(UsuarioMessages.USUARIO_NAO_ENCONTRADO));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> buscaGlobal(String termo, Pageable pageable) {

        if (termo == null || termo.isBlank()) {
            return Page.empty(pageable);
        }

        String termoLimpo = termo.trim();

        if (termoLimpo.length() < 3) {
            throw new BusinessException(UsuarioMessages.TERMO_BUSCA_CURTO);
        }

        return userRepository.buscaGlobal(termoLimpo, pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);

        if (!dto.login().equals(usuario.getLogin())) {
            if (userRepository.existsByLogin(dto.login())) {
                throw new BusinessException(UsuarioMessages.LOGIN_JA_CADASTRADO);
            }
        }

        usuarioMapper.atualizarDados(dto, usuario);
        Usuario usuarioSalvo = userRepository.save(usuario);

        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        userRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UsuarioMessages.USUARIO_NAO_ENCONTRADO));
    }
}