package br.com.gilbertodev.apipetshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiPetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPetshopApplication.class, args);
    }

}
