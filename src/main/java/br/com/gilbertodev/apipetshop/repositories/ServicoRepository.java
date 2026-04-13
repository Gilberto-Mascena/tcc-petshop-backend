package br.com.gilbertodev.apipetshop.repositories;

import br.com.gilbertodev.apipetshop.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByPetId(Long petId);
}
