package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.enums.TipoServico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servico extends EntidadeBase {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoServico tipo;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private BigDecimal valorBase;
}