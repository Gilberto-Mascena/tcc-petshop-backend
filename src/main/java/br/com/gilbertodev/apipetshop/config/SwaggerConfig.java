package br.com.gilbertodev.apipetshop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Pet Shop")
                        .version("1.0")
                        .description("""
                                ### Sistema de Controle de Fluxo e Atendimentos
                                API desenvolvida para o gerenciamento completo de um pet shop, abrangendo:
                                
                                * **Funcionários:** Gestão de usuários e permissões de acesso.
                                * **Tutores & Pets:** Cadastro unificado de clientes e seus respectivos animais.
                                * **Agendamentos:** Controle dinâmico e marcação de serviços (banho, tosa, etc.).
                                """)
                        .contact(new Contact()
                                .name("Gilberto | Dev")
                                .email("gilbertomascena@gmail.com")));
    }
}
