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
                        .title("API Pet Shop - Sistema de Gerenciamento")
                        .version("1.0")
                        .description("API para controle de pets, clientes e agendamentos de banho e tosa.")
                        .contact(new Contact()
                                .name("Gilberto | Dev")
                                .email("gilbertomascena@gmail.com")
                                .url("https://github.com/Gilberto-Mascena")));
    }
}
