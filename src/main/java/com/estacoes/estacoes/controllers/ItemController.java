package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    public Flux<ItemContrato> listarItens(@PathVariable Long id) {

        return contratoService.findById(id)
                .map(c -> c.getItensContrato()).flatMapIterable(i -> i);
    }
}
