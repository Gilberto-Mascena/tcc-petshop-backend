package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByCpf(String cpf);

    Page<Tutor> findAll(Pageable paginacao);

    @Query("SELECT t FROM Tutor t WHERE " +
            "LOWER(t.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(t.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(t.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(t.celular) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Tutor> buscaGlobal(String termo, Pageable paginacao);
}
