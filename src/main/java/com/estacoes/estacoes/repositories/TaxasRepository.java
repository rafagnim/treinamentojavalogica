package com.estacoes.estacoes.repositories;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.persistence.Query;
import java.util.List;

@Repository
public interface TaxasRepository extends JpaRepository<Taxa, Integer> {
    String findByDescription(String description);
}
