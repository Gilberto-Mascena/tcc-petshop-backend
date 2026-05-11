package br.com.gilbertodev.apipetshop.integration;

import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Tutor;
import br.com.gilbertodev.apipetshop.repositories.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PetIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TutorRepository tutorRepository;

    String urlPets = "/api/pets";

    String jsonPetInvalido = """
            {
              "nome": "",
              "especie": "",
              "tutorId": 1,
              "porte": "GRANDE"
            }
            """;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve cadastrar um Pet vinculado a um Tutor existente")
    void deveCadastrarPetComSucesso() throws Exception {

        Tutor tutorSalvo = criarTutorTeste();

        String jsonValido = """
                {
                  "nome": "Rex",
                  "especie": "CACHORRO",
                  "raca": "Labrador",
                  "dataNascimento": "2002-01-01",
                  "observacoes": "",
                  "tutorId": %d,
                  "porte": "GRANDE"
                }
                """.formatted(tutorSalvo.getId());

        mockMvc.perform(post(urlPets)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Rex"));
    }

    private Tutor criarTutorTeste() {
        Tutor tutor = new Tutor();
        tutor.setNome("Bob Blue");
        tutor.setEmail("bob@gmail.com");
        tutor.setCpf("12345678901");
        tutor.setCelular("11999999999");
        tutor.setEndereco(new Endereco(
                "MG",
                "Belo Horizonte",
                "Centro",
                "Rua Exemplo",
                "1234",
                "",
                "12345-678"
        ));

        return tutorRepository.save(tutor);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve devolver (400) Bad Request ao cadastrar Pet com dados inválidos")
    void deveRetornarBadRequestAoCadastrarPetComDadosInvalidos() throws Exception {

        mockMvc.perform(post(urlPets)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPetInvalido))
                .andExpect(status().isBadRequest());
    }
}
