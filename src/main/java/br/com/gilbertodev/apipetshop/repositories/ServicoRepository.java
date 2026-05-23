package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Page<Servico> findAll(Pageable paginacao);

    @Query("SELECT s FROM Servico s WHERE " +
            "LOWER(s.tipo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(s.observacoes) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "CAST(s.valorBase as string ) LIKE (CONCAT('%', :termo, '%'))")
    Page<Servico> buscaGlobal(@Param("termo") String termo, Pageable paginacao);
}
