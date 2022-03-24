package com.estacoes.estacoes;

import org.springframework.http.HttpStatus;
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
                .filter(e -> RedeEstacoes.estacaoMap.containsKey(e[0]) && RedeEstacoes.estacaoMap.containsKey(e[1]))
                .switchIfEmpty(errorIllegalArgumentException())
                .flatMap(e-> {
                    var r = rede.retornaMenorRota(e[0], e[1]);
                    return r;
                        }
                );
    }

    private Mono<? extends String[]> errorIllegalArgumentException() {
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe duas estações válidas"));
    }
}
