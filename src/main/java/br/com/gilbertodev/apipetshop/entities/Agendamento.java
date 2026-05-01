package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.enums.messages.AgendamentoMessages;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento extends EntidadeBase {

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public void calcularValorFinal() {
        if (this.servico == null || this.pet == null) {
            throw new BusinessException(AgendamentoMessages.DADOS_INCOMPLETOS_PARA_CALCULO);
        }

        BigDecimal valorBase = this.servico.getValorBase();
        BigDecimal multiplicador = switch (this.pet.getPorte()) {
            case MEDIO -> new BigDecimal("1.20");
            case GRANDE -> new BigDecimal("1.50");
            default -> BigDecimal.ONE;
        };

        this.valorTotal = valorBase.multiply(multiplicador).setScale(2, RoundingMode.HALF_UP);
    }
}