package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.dtos.tutor.TutorRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_tutor")
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    private String telefone;
    private String celular;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    public void atualizarDados(TutorRequestDTO dados) {
        this.nome = dados.getNome();
        this.email = dados.getEmail();
        this.cpf = dados.getCpf();
        this.telefone = dados.getTelefone();
        this.celular = dados.getCelular();

        if (dados.getEndereco() != null) {
            if (this.endereco == null) this.endereco = new Endereco();
            this.endereco.atualizarDados(dados.getEndereco());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
