package br.com.gilbertodev.apipetshop.entities;

import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status = StatusAgendamento.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;
    private String observacoes;

    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();

        if (this.status == null) {
            this.status = StatusAgendamento.PENDENTE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

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
