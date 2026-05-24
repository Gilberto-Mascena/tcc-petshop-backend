package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @EntityGraph(attributePaths = {"pet", "pet.tutor", "servico"})
    Page<Agendamento> findAll(Pageable paginacao);

    boolean existsByDataHoraAndStatusNot(LocalDateTime dataHora, StatusAgendamento status);

    @Query("SELECT a FROM Agendamento a JOIN a.pet p JOIN a.servico s WHERE " +
            "CAST(a.dataHora as string ) LIKE CONCAT('%', :termo, '%') OR " +
            "LOWER(CAST(a.status as string )) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(s.tipo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "CAST(a.valorTotal as string) LIKE CONCAT('%', :termo, '%') OR " +
            "LOWER(a.observacoes) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Agendamento> buscaGlobal(@Param("termo") String termo, Pageable paginacao);
}