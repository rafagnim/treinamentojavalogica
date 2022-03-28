package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ValidaCNPJ;
import com.estacoes.estacoes.services.ValidaCPF;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("contrato")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public Mono<Contrato> saveContrato(@RequestBody Contrato contrato) {
        Integer vl_contrato = Math.toIntExact(Math.round(contrato.getVl_contrato()));
        return Mono.just(contrato)
                .filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Informe um Cpf ou CNPJ válido")))
                .filter(e -> validaValor(e))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Informe valores com até duas casas decimais")))
                .flatMap(e-> contratoService.saveContrato(e)
                );
    }

    private boolean validaValor(Contrato e) {
        Double valor = e.getVl_contrato() * 100;
        return valor - valor.intValue() == 0 ? true : false;
    }
}
