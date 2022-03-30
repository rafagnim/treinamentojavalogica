package com.estacoes.estacoes.repositories;

import com.estacoes.estacoes.entities.Contrato;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    @Query(value = "select * from tb_contrato where cpf_cnpj = ?1", nativeQuery = true)
    List<Contrato> findCpfCnpj(String cpf_cnpj);

}
