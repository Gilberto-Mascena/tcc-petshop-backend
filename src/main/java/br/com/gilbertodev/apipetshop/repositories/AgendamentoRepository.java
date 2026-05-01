package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Page<Agendamento> findAll(Pageable paginacao);
}
