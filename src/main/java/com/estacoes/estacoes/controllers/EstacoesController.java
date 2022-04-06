package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.RedeEstacoes;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Set;

@RestController
@RequestMapping(path = "estacoes")
public class EstacoesController {

    RedeEstacoes rede = new RedeEstacoes();

    @PostMapping(path = "rotas")
    public Mono<String[]> rotas(@RequestBody String[] estacoes) {
        return Mono.just(estacoes)
                .filter(e -> e.length == 2)
                .switchIfEmpty(errorIllegalArgumentExceptionNumberStations())
                .filter(e -> RedeEstacoes.estacaoMap.containsKey(e[0]) && RedeEstacoes.estacaoMap.containsKey(e[1]))
                .switchIfEmpty(errorIllegalArgumentExceptionErrorName())
                .flatMap(e-> {
                    var r = rede.retornaMenorRota(e[0], e[1]);
                    return r;
                        }
                );
    }

    @GetMapping(path = "listaestacoes")
    public Mono<Set<String>> listaEstacoes() {
        return Mono.just(RedeEstacoes.estacaoMap.keySet());
    }

    private <T> Mono<T> errorIllegalArgumentExceptionNumberStations() {
        return Mono.error(new IllegalArgumentException("Informe duas estações"));
    }

    private <T> Mono<T> errorIllegalArgumentExceptionErrorName() {
        return Mono.error(new IllegalArgumentException("Erro no nome das estações"));
    }
}
