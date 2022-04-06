package com.estacoes.estacoes.services;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.repositories.ContratoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ItemService itemService;

    public ContratoService(ContratoRepository contratoRepository, ItemService itemService) {
        this.contratoRepository = contratoRepository;
        this.itemService = itemService;
    }

    public Mono<Contrato> saveContrato (Contrato c) {
        return Mono.just(contratoRepository.save(c));
    }

    public Mono<Contrato> upDateItensContrato (Integer id, ItemContrato item) {
        return this.findById(id)
                .map (c -> {
                    var itens = c.getItem();
                    itens.add(item);
                    c.setVl_contrato(c.getVl_contrato() + item.getVl_duplicata());
                    c.setItem(itens);
                    var contrato = contratoRepository.save(c);
                    return contrato;
                });
    }

    public Mono<Contrato> excluiItensContrato (Integer contratoId, ItemContrato item) {
        return this.findById(contratoId)
                .flatMap(c -> {
                    var itens = c.getItem();
                    var itensRestantes = itens.stream().filter(i -> {
                        return i.getId() != item.getId();
                    }).collect(Collectors.toList());
                    c.setVl_contrato(c.getVl_contrato() - item.getVl_duplicata());
                    c.setItem(itensRestantes);
                    itemService.excluir(item.getId());
                    return this.saveContrato(c);
                });
    }

    public Mono<Contrato> findById (Integer id) {
        return Mono.justOrEmpty(contratoRepository.findById(id).get());
    }

    public Flux<Contrato> getContratoByCpfCnpj (String cpf_cnpj) {
        return Flux.fromIterable(contratoRepository.findCpfCnpj(cpf_cnpj));
    }
}
