package com.estacoes.estacoes.controllers;

import com.estacoes.estacoes.entities.Contrato;
import com.estacoes.estacoes.entities.ItemContrato;
import com.estacoes.estacoes.entities.Mensagem;
import com.estacoes.estacoes.services.ContratoService;
import com.estacoes.estacoes.services.ValidaCNPJ;
import com.estacoes.estacoes.services.ValidaCPF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("contrato")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView consultarEstacoes() {
        ModelAndView mv = new ModelAndView("contratos/cadastro.html");
        mv.addObject("contrato", new Contrato());
        return mv;
    }

    @RequestMapping(path = "cadastrar", method = RequestMethod.POST)
    public ModelAndView cadastrarContrato(Contrato contrato) {

        ModelAndView mv = new ModelAndView("contratos/cadastro.html");

        mv.addObject("obj", Mono.just(contrato)
                .filter(e -> ValidaCPF.valida(e.getCpf_cnpj()) || ValidaCNPJ.valida(e.getCpf_cnpj()))
                .flatMap(e-> contratoService.saveContrato(e))
                .map(c -> {
                    return new Mensagem("Contrato " + c.getCpf_cnpj() + " R$ " + c.getVl_contrato() + " cadastrado com sucesso");
                })
                .defaultIfEmpty(new Mensagem("Informe um CPF ou CNPJ válido"))
                .flatMapIterable(i -> {
                    return Arrays.asList(i);
                })
                .toIterable());
        return mv;
    }
}
