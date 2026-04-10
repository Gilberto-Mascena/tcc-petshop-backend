package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.enums.PortePet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_pet")
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne()
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortePet porte;

    public void atualizarDados(PetRequestDTO dados) {
        this.nome = dados.getNome();
        this.especie = dados.getEspecie();
        this.raca = dados.getRaca();
        this.dataNascimento = dados.getDataNascimento();
        this.observacoes = dados.getObservacoes();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
