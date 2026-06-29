package br.com.gilbertodev.apipetshop.infra.config;

import br.com.gilbertodev.apipetshop.entities.Role;
import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.repositories.RoleRepository;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class UsuarioInicialConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UsuarioInicialConfig.class);

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioInicialConfig(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        criarRoleSeNaoExistir("ROLE_ADMIN");
        criarRoleSeNaoExistir("ROLE_GERENTE");
        criarRoleSeNaoExistir("ROLE_ATENDENTE");
        criarRoleSeNaoExistir("ROLE_CLIENTE");

        if (usuarioRepository.count() == 0) {

            Role roleAdmin = roleRepository.findByNome("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Erro ao inicializar: ROLE_ADMIN não encontrada."));

            var senhaCriptografada = passwordEncoder.encode("admin123");
            var admin = new Usuario("admin@petshop.com", senhaCriptografada);

            admin.setRoles(Set.of(roleAdmin));

            usuarioRepository.save(admin);

            log.info(">>> [SEED] Banco de dados vazio. Usuário admin criado com sucesso!");

        } else {
            log.info(">>> [SEED] Usuários já detectados no banco. Pulando criação inicial.");
        }
    }

    private void criarRoleSeNaoExistir(String nomeRole) {

        if (!roleRepository.existsByNome(nomeRole)) {
            roleRepository.save(new Role(nomeRole));
            log.info(">>> [SEED] Role '{}' criada com sucesso!", nomeRole);
        }
    }
}
