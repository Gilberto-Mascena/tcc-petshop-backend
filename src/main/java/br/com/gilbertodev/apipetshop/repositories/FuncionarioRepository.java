package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT f FROM Funcionario f WHERE " +
            "LOWER(f.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.telefone) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.celular) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Funcionario> buscaGlobal(String termo, Pageable paginacao);
}
