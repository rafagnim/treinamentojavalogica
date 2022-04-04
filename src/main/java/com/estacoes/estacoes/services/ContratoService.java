package com.estacoes.estacoes.services;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.repositories.ContratoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public Mono<Contrato> saveContrato (Contrato c) {
        return Mono.just(contratoRepository.save(c));
    }

    public Mono<Contrato> upDateItensContrato (Integer id, ItemContrato item) {
        return this.findById(id)
                .map (c -> {
                    var itens = c.getItem();
                    itens.add(item);
                    c.setItem(itens);
                    var contrato = contratoRepository.save(c);
                    return contrato;
                });
    }

    public Mono<Contrato> findById (Integer id) {
        return Mono.justOrEmpty(contratoRepository.findById(id).get());
    }

    public Flux<Contrato> getContratoByCpfCnpj (String cpf_cnpj) {
        return Flux.fromIterable(contratoRepository.findCpfCnpj(cpf_cnpj));
    }
}
