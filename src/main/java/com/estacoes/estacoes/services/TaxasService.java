package com.estacoes.estacoes.services;

import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class TaxasService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String execute(String sql) {
        return entityManager.createNativeQuery(sql).getSingleResult().toString();
    }
}
