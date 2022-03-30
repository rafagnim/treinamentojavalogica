package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.entities.Mensagem;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ItemService;
import com.estacoes.estacoes.services.ValidaCNPJ;
import com.estacoes.estacoes.services.ValidaCPF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Controller
@RequestMapping("item")
public class ItemController {

    private final ItemService itemService;
    private final ContratoService contratoService;

    public ItemController(ItemService itemService, ContratoService contratoService) {
        this.itemService = itemService;
        this.contratoService = contratoService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView cadastrarItem() {
        ModelAndView mv = new ModelAndView("item/cadastro.html");
        //mv.addObject("itemcontrato", new ItemContrato());
        return mv;
    }

//            mv.addObject("obj", Mono.just(contrato)
//            .filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
//            .flatMap(e-> contratoService.saveContrato(e))
//            .map(c -> {
//        return new Mensagem("Contrato " + c.getCpf_cnpj() + " R$ " + c.getVl_contrato() + " cadastrado com sucesso");
//    })
//            .defaultIfEmpty(new Mensagem("Informe um CPF ou CNPJ válido"))
//            .flatMapIterable(i -> {
//        return Arrays.asList(i);
//    })
//            .toIterable());

    @RequestMapping(path = "buscarcontrato")
    public ModelAndView buscarContrato(String cpf_cnpj) {
        ModelAndView mv = new ModelAndView("item/cadastro.html");
        var retorno = Mono.just(cpf_cnpj)
                        .filter(e -> ValidaCPF.valida(e) || ValidaCNPJ.valida(e))
                        .concatWith(Mono.empty())
                        .flatMap(e -> contratoService.getContratoByCpfCnpj(cpf_cnpj))
                        .toIterable();
        mv.addObject("listaContratos", retorno);

        return mv;
    }

    @RequestMapping(path = "cadastrar", method = RequestMethod.POST)
    public ModelAndView cadastrarContrato(ItemContrato item) {

        ModelAndView mv = new ModelAndView("item/cadastro.html");

        //SALVAR O CONTRATO COM A LISTA
//        mv.addObject("obj", Mono.just(item)
//                //.filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
//                .flatMap(e-> itemService.saveItem(e))
//                .map(c -> {
//                    return new Mensagem("Item salvo com sucesso");
//                })
//                //.defaultIfEmpty(new Mensagem("Informe um CPF ou CNPJ válido"))
//                .flatMapIterable(i -> {
//                    return Arrays.asList(i);
//                })
//                .toIterable());
        mv.addObject("itemcontrato", new ItemContrato());
        return mv;
    }
}
