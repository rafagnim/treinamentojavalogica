package com.estacoes.estacoes.services;

import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Mono<ItemContrato> saveItem (ItemContrato i) {
        return Mono.just(itemRepository.save(i));
    }

    public Mono<ItemContrato> getById(Integer itemId) {
        return Mono.just(itemRepository.getById(itemId));
    }

    public void excluir (Integer itemId) {
        itemRepository.deleteById(itemId);
    }
}
