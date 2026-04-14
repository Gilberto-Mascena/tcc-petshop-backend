package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.enums.messages.ServicoMessages;
import br.com.gilbertodev.apipetshop.enums.TipoServico;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoServico tipo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public void aplicarPrecoPadrao() {
        if (this.tipo == null) return;

        this.valor = switch (this.tipo) {
            case BANHO -> new BigDecimal("50.00");
            case TOSA -> new BigDecimal("60.00");
            case BANHO_E_TOSA -> new BigDecimal("100.00");
            case CORTE_UNHA -> new BigDecimal("25.00");
            case LIMPEZA_OUVIDO -> new BigDecimal("20.00");
        };
    }

    public void calcularValorFinal() {

        if (this.tipo == null) {
            throw new BusinessException(ServicoMessages.TIPO_SERVICO_NULO);
        }
        if (this.pet == null || this.pet.getPorte() == null) {
            throw new BusinessException(ServicoMessages.PET_OU_PORTE_NULO);
        }

        BigDecimal base = getValorBase();

        BigDecimal multiplicador = switch (this.pet.getPorte()) {
            case PEQUENO -> new BigDecimal("1.0");
            case MEDIO -> new BigDecimal("1.2");
            case GRANDE -> new BigDecimal("1.5");
        };

        this.valor = base.multiply(multiplicador);
    }

    private BigDecimal getValorBase() {
        return switch (this.tipo) {
            case BANHO -> new BigDecimal("50.00");
            case TOSA -> new BigDecimal("60.00");
            case BANHO_E_TOSA -> new BigDecimal("100.00");
            case CORTE_UNHA -> new BigDecimal("25.00");
            case LIMPEZA_OUVIDO -> new BigDecimal("20.00");
        };
    }
}
