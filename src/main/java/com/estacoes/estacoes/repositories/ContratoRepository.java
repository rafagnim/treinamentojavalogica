package com.estacoes.estacoes.repositories;

import com.estacoes.estacoes.entities.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    @Query(value = "select * from tb_contrato where cpf_cnpj = ?1", nativeQuery = true)
    List<Contrato> findCpfCnpj(String cpf_cnpj);

}
