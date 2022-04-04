package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ItemService;
import com.estacoes.estacoes.services.ValidaCNPJ;
import com.estacoes.estacoes.services.ValidaCPF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    private final ItemService itemService;
    private final ContratoService contratoService;

    public ItemController(ItemService itemService, ContratoService contratoService) {
        this.itemService = itemService;
        this.contratoService = contratoService;
    }

    @RequestMapping(path = "listar/{id}")
    public Flux<ItemContrato> listarItens(@PathVariable Integer id) {

        return contratoService.findById(id)
                .map(c -> {
                    return c.getItem();
                }).flatMapIterable(i -> {
            return i;
        });
    }
}
