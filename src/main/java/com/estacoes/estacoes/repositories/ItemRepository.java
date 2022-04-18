package com.estacoes.estacoes.repositories;

import com.estacoes.estacoes.entities.ItemContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemContrato, Long> {
}
