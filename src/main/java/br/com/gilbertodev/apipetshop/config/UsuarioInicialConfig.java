package br.com.gilbertodev.apipetshop.config;

import br.com.gilbertodev.apipetshop.entities.Usuario;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioInicialConfig implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioInicialConfig(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (repository.count() == 0) {

            var senhaCriptografada = passwordEncoder.encode("admin123");
            var admin = new Usuario("admin@petshop.com", senhaCriptografada);

            repository.save(admin);

            System.out.println(">>> [SEED] Banco de dados vazio. Usuário admin criado com sucesso!");
        } else {
            System.out.println(">>> [SEED] Usuários já detectados no banco. Pulando criação inicial.");
        }
    }
}