package com.estacoes.estacoes.services;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.Emprestimo;
import com.estacoes.estacoes.repositories.ContratoRepository;
import com.estacoes.estacoes.repositories.EmprestimoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final ItemService itemService;

    public ContratoService(ContratoRepository contratoRepository, EmprestimoRepository emprestimoRepository, ItemService itemService) {
        this.contratoRepository = contratoRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.itemService = itemService;
    }

    public Mono<Contrato> saveContrato (Contrato c) {
        return Mono.just(contratoRepository.save(c));
    }

    public Mono<Emprestimo> saveSimulacao (Emprestimo e) {
        return Mono.just(emprestimoRepository.save(e));
    }

//    public Mono<Contrato> upDateItensContrato (Integer id, ItemContrato item) {
//        return this.findById(id)
//                .map (c -> {
//                    var itens = c.getItensContrato();
//                    itens.add(item);
//                    c.setVl_contrato(c.getVl_contrato() + item.getVl_duplicata());
//                    c.setItensContrato(itens);
//                    var contrato = contratoRepository.save(c);
//                    return contrato;
//                });
//    }

//    public Mono<Contrato> excluiItensContrato (Integer contratoId, ItemContrato item) {
//        return this.findById(contratoId)
//                .flatMap(c -> {
//                    var itens = c.getItensContrato();
//                    var itensRestantes = itens.stream().filter(i -> {
//                        return i.getId() != item.getId();
//                    }).collect(Collectors.toList());
//                    c.setVl_contrato(c.getVl_contrato() - item.getVl_duplicata());
//                    c.setItensContrato(itensRestantes);
//                    itemService.excluir(item.getId());
//                    return this.saveContrato(c);
//                });
//    }

    public Mono<Contrato> findById (Long id) {
        return Mono.justOrEmpty(contratoRepository.findById(id).get());
    }

    public Flux<Contrato> getContratoByCpfCnpj (String cpf_cnpj) {
        return Flux.fromIterable(contratoRepository.findCpfCnpj(cpf_cnpj));
    }
}
