package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.config.ComponentConfig;
import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.Emprestimo;
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
        Contrato c1 = contrato;
        var retorno =  Mono.just(contrato)
                .filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
                .flatMap(e-> contratoService.saveContrato(e))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Informe um CPF ou CNPJ válido.")));
        return retorno;
    }

    @GetMapping(path = "consultar/{id}")
    public Mono<Contrato> getById(@PathVariable Long id) {
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

    @PostMapping(path = "emprestimo")
    public Mono<Emprestimo> simulaEmprestimo(@RequestBody Emprestimo emprestimo) {
        return Mono.just(emprestimo)
                .flatMap(
                        e -> {e.calculaParcelas();
                            return contratoService.saveSimulacao(e);
                        });
    }

    @GetMapping(path= "taxas")
    public Mono<String> obterTaxa() {
        return Mono.just(ComponentConfig.getTAX_PATTERN());
    }
}
