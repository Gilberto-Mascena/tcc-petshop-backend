package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.dtos.pet.PetRequestDTO;
import br.com.gilbertodev.apipetshop.enums.PortePet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tb_pet")
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especie;

    private String raca;

    private LocalDate dataNascimento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortePet porte;

    public void atualizarDados(PetRequestDTO dados) {
        if (dados.getNome() != null) this.nome = dados.getNome();
        if (dados.getEspecie() != null) this.especie = dados.getEspecie();
        if (dados.getRaca() != null) this.raca = dados.getRaca();
        if (dados.getDataNascimento() != null) this.dataNascimento = dados.getDataNascimento();
        if (dados.getObservacoes() != null) this.observacoes = dados.getObservacoes();
        if (dados.getPorte() != null) this.porte = dados.getPorte();
    }
}
