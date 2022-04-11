package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ItemService;
import com.estacoes.estacoes.services.ValidaCNPJ;
import com.estacoes.estacoes.services.ValidaCPF;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("contrato")
public class ContratoController {

    private final ContratoService contratoService;
    private final ItemService itemService;

    public ContratoController(ContratoService contratoService, ItemService itemService) {
        this.contratoService = contratoService;
        this.itemService = itemService;
    }

    @PostMapping(path = "cadastrar")
    public Mono<Contrato> cadastraContrato(@RequestBody Contrato contrato) {
        var retorno =  Mono.just(contrato)
                .filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
                .flatMap(e-> contratoService.saveContrato(e))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Informe um CPF ou CNPJ válido.")));
        return retorno;
    }

    @PutMapping(path = "atualizaritens/{id}")
    public Mono<Contrato> atualizarContrato(@RequestBody ItemContrato item, @PathVariable Integer id) {
        item.setContrato_id(id);
        return this.contratoService.upDateItensContrato(id, item);
    }

    @PutMapping(path = "excluiitem/{id}")
    public Mono<Contrato> excluiItemContrato(@PathVariable Integer id) {
        return itemService.getById(id)
                .flatMap(i -> contratoService.excluiItensContrato(i.getContrato_id(), i));
    }

    @GetMapping(path = "consultar/{id}")
    public Mono<Contrato> getById(@PathVariable Integer id) {
        return contratoService.findById(id);
    }

    @GetMapping(path = "listar/{cpf_cnpj}")
    public Flux<Contrato> listarContratos(@PathVariable String cpf_cnpj) {
        return Mono.just(cpf_cnpj)
                .filter(e -> ValidaCPF.valida(cpf_cnpj) || ValidaCNPJ.valida(cpf_cnpj))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Informe um CPF ou CNPJ válido.")))
                .concatWith(Mono.empty())
                .flatMap(e -> contratoService.getContratoByCpfCnpj(cpf_cnpj));
    }

    @GetMapping(path = "valida/{cpf_cnpj}")
    public Mono<Boolean> validaID(@PathVariable String cpf_cnpj) {
        return Mono.just(cpf_cnpj)
                .map(e -> {
                    return (ValidaCPF.valida(cpf_cnpj) || ValidaCNPJ.valida(cpf_cnpj));
                });
    }
}
