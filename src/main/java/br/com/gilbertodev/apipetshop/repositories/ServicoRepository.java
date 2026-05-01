package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Page<Servico> findAll(Pageable paginacao);
}
