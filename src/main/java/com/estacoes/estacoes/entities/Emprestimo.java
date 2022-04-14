package com.estacoes.estacoes.entities;

import com.estacoes.estacoes.config.ComponentConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {

    private String cpf_cnpj;
    private BigDecimal valor;
    private Integer parcelas;
    private BigDecimal taxaMensal;
    private String taxaAnual;
    private List<Parcela> parcelasResultado;

    public Emprestimo() {
    }

    public Emprestimo(String cpf_cnpj, BigDecimal valor, Integer parcelas, List<Parcela> parcelasResultado) {
        this.cpf_cnpj = cpf_cnpj;
        this.valor = valor;
        this.parcelas = parcelas;

        this.parcelasResultado = parcelasResultado;
    }
    public void calculaParcelas() {
        this.taxaAnual = ComponentConfig.TAX_PATTERN;
        Double taxaAnual = Double.parseDouble(this.taxaAnual);
        this.taxaMensal = BigDecimal.valueOf((Math.pow((1 + (taxaAnual / 100)), 1D / 12D)) - 1).setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal.valueOf(1);
        BigDecimal um = new BigDecimal("1");
        BigDecimal operador = taxaMensal.add(um).pow(parcelas);
        BigDecimal parcelaPrecisa = this.valor.multiply(((operador.multiply(taxaMensal)).divide(operador.subtract(um), RoundingMode.HALF_EVEN))).setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal parcela = parcelaPrecisa.setScale(2, RoundingMode.HALF_DOWN);
        parcelasResultado = new ArrayList<>();
        parcelasResultado.add(new Parcela(0, null, null, null, valor));
        for (int i = 1; i <= parcelas; i ++) {
            var saldoDevedorAnterior = parcelasResultado.get(i-1).getSaldoDevedor();
            var juros = saldoDevedorAnterior.multiply(taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
            var amortizacao = parcela.subtract(juros);
            if (i == parcelas) {
                parcelasResultado.add(new Parcela(i, saldoDevedorAnterior, juros, saldoDevedorAnterior.add(juros),saldoDevedorAnterior.subtract(saldoDevedorAnterior)));
                break;
            }
            parcelasResultado.add(new Parcela(i, amortizacao, juros, parcela,saldoDevedorAnterior.subtract(amortizacao)));
        }
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public List<Parcela> getParcelasResultado() {
        return parcelasResultado;
    }

    public void setParcelasResultado(List<Parcela> parcelasResultado) {
        this.parcelasResultado = parcelasResultado;
    }

    public BigDecimal getTaxaMensal() {
        return taxaMensal;
    }

    public void setTaxaMensal(BigDecimal taxaMensal) {
        this.taxaMensal = taxaMensal;
    }

    public String getTaxaAnual() {
        return taxaAnual;
    }

    public void setTaxaAnual(String taxaAnual) {
        this.taxaAnual = taxaAnual;
    }
}
