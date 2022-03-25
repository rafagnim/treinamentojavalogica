package com.estacoes.estacoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("estacoes")
public class EstacoesController {

    RedeEstacoes rede = new RedeEstacoes();

    @GetMapping()
    public Mono<String[]> broadcast(@RequestBody String[] estacoes) {
        rede.setUpEstacoes();
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

    private <T> Mono<T> errorIllegalArgumentExceptionNumberStations() {
        return Mono.error(new IllegalArgumentException("Informe duas estações"));
    }

    private <T> Mono<T> errorIllegalArgumentExceptionErrorName() {
        return Mono.error(new IllegalArgumentException("Erro no nome das estações"));
    }
}
