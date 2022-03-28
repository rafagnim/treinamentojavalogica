package com.estacoes.estacoes.services;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.repositories.ContratoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public Mono<Contrato> saveContrato (Contrato c) {
        return Mono.just(contratoRepository.save(c));
    }
}
