package br.com.gilbertodev.apipetshop.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    String urlLogin = "/auth/login";
    String urlTutores = "/api/tutores";

    String jsonValidoParaLogin = """
            {
                "login": "admin@petshop.com",
                "password": "admin123"
            }
            """;

    String jsonInvalidoParaLogin = """
            {
                 "login": "admin123",
                 "password": "admin123"
            }
            """;

    @Test
    @DisplayName("Deve devolver (200) e um token JWT válido ao fazer login com dados corretos")
    void deveAutenticarUsuarioERetornarToken() throws Exception {

        mockMvc.perform(post(urlLogin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValidoParaLogin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("Deve devolver (401) Unauthorized ao fazer login com credenciais inválidas")
    void deveRetornarUnauthorizedAoFazerLoginComCredenciaisInvalidas() throws Exception {

        mockMvc.perform(post(urlLogin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalidoParaLogin))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve devolver (400) Bad Request ao fazer login sem JSON")
    void deveRetornarBadRequestAoFazerLoginSemJSON() throws Exception {

        mockMvc.perform(post(urlLogin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve barrar (403) tentativa de cadastro sem autenticação")
    void deveRetornarForbiddenAoCadastrarSemToken() throws Exception {

        mockMvc.perform(post(urlTutores)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isForbidden());
    }
}