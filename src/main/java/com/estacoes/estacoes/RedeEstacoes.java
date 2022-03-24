package com.estacoes.estacoes;

import reactor.core.publisher.Mono;

import java.util.*;

public class RedeEstacoes {

    private Estacao se;
    private Estacao paulista;
    private Estacao brigadeiro;
    private Estacao fariaLima;
    private Estacao saoLuis;
    private Estacao ipiranga;
    private Estacao pinheiros;
    private Estacao barrafunda;
    private Estacao brigadeiro1;
    protected static Map<String, Estacao> estacaoMap;
    private Estacao origem;
    private Estacao destino;
    private List<String[]> retorno = new ArrayList<>();

    public void setUpEstacoes() {

        se = new Estacao("Estação Sé");
        paulista = new Estacao("Estação Paulista");
        brigadeiro = new Estacao("Estação Brigadeiro");
        fariaLima = new Estacao("Estação Faria Lima");
        saoLuis = new Estacao("Estação São Luis");
        ipiranga = new Estacao("Estação Ipiranga");
        pinheiros = new Estacao("Estação Pinheiros");
        barrafunda = new Estacao("Estação Barra Funda");
        brigadeiro1 = new Estacao("Estação Brigadeiro1");

        se.setV1(paulista);
        se.setV2(brigadeiro1);
        se.setV3(fariaLima);

        paulista.setV1(brigadeiro);
        paulista.setV2(brigadeiro1);
        paulista.setV3(se);

        brigadeiro.setV1(paulista);
        brigadeiro.setV2(saoLuis);

        saoLuis.setV1(brigadeiro);
        saoLuis.setV2(barrafunda);

        barrafunda.setV1(saoLuis);
        barrafunda.setV2(ipiranga);
        barrafunda.setV3(pinheiros);

        ipiranga.setV1(barrafunda);
        ipiranga.setV2(brigadeiro1);

        brigadeiro1.setV1((paulista));
        brigadeiro1.setV2(se);
        brigadeiro1.setV3(ipiranga);

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
        estacaoMap.put("Estação Brigadeiro1", brigadeiro1);
    }

    public Mono<String[]> retornaMenorRota(String e1, String e2) {

        origem = estacaoMap.get(e1);
        destino = estacaoMap.get(e2);

        int profundidade = 0;
        String strDivs = "";
        tracaRota(origem, strDivs, profundidade);

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

    private void tracaRota(Estacao atual, String strDivs, int profundidade) {
        profundidade++;
        if (profundidade < 5 && !atual.getNome().equals(destino.getNome())) {
            strDivs += atual.getNome() + "-";
            if (atual.getV1() != null) {
                tracaRota(atual.getV1(), strDivs, profundidade);
            }
            if (atual.getV2() != null) {
                tracaRota(atual.getV2(), strDivs, profundidade);
            }
            if (atual.getV3() != null) {
                tracaRota(atual.getV3(), strDivs, profundidade);
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
