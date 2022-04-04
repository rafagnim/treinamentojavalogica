package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.dtos.EstacaoConsultaDTO;
import com.estacoes.estacoes.entities.RedeEstacoes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "estacoes")
public class EstacoesController {

    RedeEstacoes rede = new RedeEstacoes();


//
//    @RequestMapping(path = "rota", method = RequestMethod.POST)
//    public ModelAndView exibeMenorRota(EstacaoConsultaDTO estacoes) {
//        ModelAndView mv = new ModelAndView("estacoes/rota.html");
//        mv.addObject("rota", Mono.just(estacoes)
//                .flatMap(e -> {
//                    return rede.retornaMenorRota(e.getEstacaoOrigem(), e.getEstacaoDestino());
//                })
//                .flatMapIterable(i -> {
//                    return Arrays.asList(i);
//                }).toIterable());
//        return mv;
//    }

    @PostMapping(path = "rotas")
    public Mono<String[]> rotas(@RequestBody String[] estacoes) {
        System.out.println("Requisição");
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
