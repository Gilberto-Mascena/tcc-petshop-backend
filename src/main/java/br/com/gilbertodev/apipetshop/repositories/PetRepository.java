package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findAll(Pageable paginacao);

    @Query("SELECT p FROM Pet p JOIN p.tutor t WHERE " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.raca) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.especie) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(t.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Pet> buscaGlobal(@Param("termo") String termo, Pageable paginacao);
}
