package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_tutor")
@NoArgsConstructor
@AllArgsConstructor
public class Tutor extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    private String telefone;

    @Column(nullable = false)
    private String celular;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    public void atualizarDados(TutorRequestDTO dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.email() != null) this.email = dados.email();
        if (dados.telefone() != null) this.telefone = dados.telefone();
        if (dados.celular() != null) this.celular = dados.celular();

        if (dados.endereco() != null) {
            if (this.endereco == null) this.endereco = new Endereco();
            this.endereco.atualizarDados(dados.endereco());
        }
    }
}
