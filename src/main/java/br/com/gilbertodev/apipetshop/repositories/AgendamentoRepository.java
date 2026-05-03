package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Otimização: Carrega os relacionamentos em uma única consulta SQL (evita N+1 e LazyInitializationException)
    @EntityGraph(attributePaths = {"pet", "pet.tutor", "servico"})
    Page<Agendamento> findAll(Pageable paginacao);

    // Validação: Checa se já existe um agendamento no mesmo horário que não esteja cancelado
    boolean existsByDataHoraAndStatusNot(LocalDateTime dataHora, StatusAgendamento status);
}