package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioCadastroRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.usuario.UsuarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Role;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.mapper.UsuarioMapper;
import br.com.gilbertodev.apipetshop.messages.UsuarioMessages;
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
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponseDTO cadastrarCliente(UsuarioCadastroRequestDTO dto) {
        if (usuarioRepository.existsByLogin(dto.login())) {
            throw new BusinessException(UsuarioMessages.LOGIN_JA_CADASTRADO);
        }

        Usuario usuario = usuarioMapper.toEntityFromCadastro(dto);

        Set<Role> roles = new HashSet<>();
        Role rolePadrao = roleRepository.findByNome("ROLE_CLIENTE")
                .orElseThrow(() -> new BusinessException(UsuarioMessages.ROLE_NAO_ENCONTRADA));
        roles.add(rolePadrao);

        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.password()));
        usuario.setRoles(roles);

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByLogin(dto.login())) {
            throw new BusinessException(UsuarioMessages.LOGIN_JA_CADASTRADO);
        }

        Set<Role> roles = new HashSet<>();
        for (String roleName : dto.roleNames()) {
            Role role = roleRepository.findByNome(roleName)
                    .orElseThrow(() -> new BusinessException(UsuarioMessages.ROLE_NAO_ENCONTRADA));
            roles.add(role);
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.password()));
        usuario.setRoles(roles);

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        return usuarioMapper.toResponseDTO(usuario);
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

        return usuarioRepository.buscaGlobal(termoLimpo, pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);

        if (!dto.login().equals(usuario.getLogin())) {
            if (usuarioRepository.existsByLogin(dto.login())) {
                throw new BusinessException(UsuarioMessages.LOGIN_JA_CADASTRADO);
            }
        }

        usuarioMapper.atualizarDados(dto, usuario);

        if (dto.password() != null && !dto.password().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.password()));
        }

        Set<Role> roles = new HashSet<>();
        for (String roleName : dto.roleNames()) {
            Role role = roleRepository.findByNome(roleName)
                    .orElseThrow(() -> new BusinessException(UsuarioMessages.ROLE_NAO_ENCONTRADA));
            roles.add(role);
        }
        usuario.setRoles(roles);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UsuarioMessages.USUARIO_NAO_ENCONTRADO));
    }
}