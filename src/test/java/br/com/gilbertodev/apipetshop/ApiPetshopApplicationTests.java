package br.com.gilbertodev.apipetshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class ApiPetshopApplicationTests {

    @Test
    void contextLoads() {
    }
}
