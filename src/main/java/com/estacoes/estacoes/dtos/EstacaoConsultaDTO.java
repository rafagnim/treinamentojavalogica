package com.estacoes.estacoes.dtos;

public class EstacaoConsultaDTO {

    private String estacaoOrigem;
    private String estacaoDestino;

    public EstacaoConsultaDTO() {
    }

    public EstacaoConsultaDTO(String estacaoOrigem, String estacaoDestino) {
        this.estacaoOrigem = estacaoOrigem;
        this.estacaoDestino = estacaoDestino;
    }

    public String getEstacaoOrigem() {
        return estacaoOrigem;
    }

    public void setEstacaoOrigem(String estacaoOrigem) {
        this.estacaoOrigem = estacaoOrigem;
    }

    public String getEstacaoDestino() {
        return estacaoDestino;
    }

    public void setEstacaoDestino(String estacaoDestino) {
        this.estacaoDestino = estacaoDestino;
    }
}
