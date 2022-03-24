package com.estacoes.estacoes;

public class Estacao {

    private String nome;

    private Estacao v1;
    private Estacao v2;
    private Estacao v3;

    public Estacao() {
    }

    public Estacao(String nome) {
        this.nome = nome;
        this.v1 = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estacao getV1() {
        return v1;
    }

    public void setV1(Estacao v1) {
        this.v1 = v1;
    }

    public Estacao getV2() {
        return v2;
    }

    public void setV2(Estacao v2) {
        this.v2 = v2;
    }

    public Estacao getV3() {
        return v3;
    }

    public void setV3(Estacao v3) {
        this.v3 = v3;
    }
}
