package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private StatusAgendamento status = StatusAgendamento.PENDENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    private String observacoes;

    public void cancelar() {
        this.status = StatusAgendamento.CANCELADO;
    }

    public void confirmar() {
        this.status = StatusAgendamento.CONFIRMADO;
    }

    public void concluir() {
        this.status = StatusAgendamento.REALIZADO;
    }

    public void reagendar(LocalDateTime novaData) {
        this.dataHora = novaData;
        this.status = StatusAgendamento.PENDENTE;
    }
}