package com.estacoes.estacoes.entities;

import reactor.core.publisher.Mono;
import java.util.*;

public class RedeEstacoes {

    public static Map<String, Estacao> estacaoMap = new HashMap<>();

    public RedeEstacoes() {
        this.setUpEstacoes();
    }

    public void setUpEstacoes() {

        Estacao se = new Estacao("Estação Sé");
        Estacao paulista = new Estacao("Estação Paulista");
        Estacao brigadeiro = new Estacao("Estação Brigadeiro");
        Estacao fariaLima = new Estacao("Estação Faria Lima");
        Estacao saoLuis = new Estacao("Estação São Luis");
        Estacao ipiranga = new Estacao("Estação Ipiranga");
        Estacao pinheiros = new Estacao("Estação Pinheiros");
        Estacao barrafunda = new Estacao("Estação Barra Funda");
        Estacao aguarasa = new Estacao("Estação Água Rasa");

        se.setV1(paulista);
        se.setV2(aguarasa);
        se.setV3(fariaLima);

        paulista.setV1(brigadeiro);
        paulista.setV2(aguarasa);
        paulista.setV3(se);

        brigadeiro.setV1(paulista);
        brigadeiro.setV2(saoLuis);

        saoLuis.setV1(brigadeiro);
        saoLuis.setV2(barrafunda);

        barrafunda.setV1(saoLuis);
        barrafunda.setV2(ipiranga);
        barrafunda.setV3(pinheiros);

        ipiranga.setV1(barrafunda);
        ipiranga.setV2(aguarasa);

        aguarasa.setV1((paulista));
        aguarasa.setV2(se);
        aguarasa.setV3(ipiranga);

        fariaLima.setV1(se);
        fariaLima.setV2(pinheiros);

        pinheiros.setV1(fariaLima);
        pinheiros.setV2(barrafunda);

        estacaoMap = new HashMap<>();
        estacaoMap.put("Estação Sé", se);
        estacaoMap.put("Estação Paulista", paulista);
        estacaoMap.put("Estação Brigadeiro" , brigadeiro);
        estacaoMap.put("Estação Faria Lima", fariaLima);
        estacaoMap.put("Estação São Luis", saoLuis);
        estacaoMap.put("Estação Ipiranga", ipiranga);
        estacaoMap.put("Estação Pinheiros", pinheiros);
        estacaoMap.put("Estação Barra Funda", barrafunda);
        estacaoMap.put("Estação Água Rasa", aguarasa);

    }

    public Mono<String[]> retornaMenorRota(String e1, String e2) {

        List<String[]> retorno = new ArrayList<>();

        Estacao origem = estacaoMap.get(e1);
        Estacao destino = estacaoMap.get(e2);
        int profundidade = 0;
        String strDivs = "";
        tracaRota(origem, strDivs, profundidade, retorno, origem, destino);

        String[] r = new String[1];
        Integer tamanho = Integer.MAX_VALUE;
        for (String[] l: retorno) {
            if (l.length < tamanho) {
                tamanho = l.length;
                r = l;
            }
        }
        Mono<String[]> rotas = Mono.just(r);
        return rotas;
    }

    private void tracaRota(Estacao atual, String strDivs, int profundidade, List<String[]> listaRetorno, Estacao o, Estacao d) {
        Estacao origem = o;
        Estacao destino = d;
        List<String[]> retorno = listaRetorno;
        profundidade++;
        if (profundidade < 5 && !atual.getNome().equals(destino.getNome())) {
            strDivs += atual.getNome() + "-";
            if (atual.getV1() != null) {
                tracaRota(atual.getV1(), strDivs, profundidade, retorno, origem, destino);
            }
            if (atual.getV2() != null) {
                tracaRota(atual.getV2(), strDivs, profundidade, retorno, origem, destino);
            }
            if (atual.getV3() != null) {
                tracaRota(atual.getV3(), strDivs, profundidade, retorno, origem, destino);
            }
        } else {
            strDivs += destino.getNome();
        }
        if (atual == destino) {
            retorno.add(strDivs.split("-"));
            return;
        }
    }
}
