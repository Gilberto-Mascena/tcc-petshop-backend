package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE " +
            "LOWER(u.login) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Usuario> buscaGlobal(@Param("termo") String termo, Pageable pageable);
}