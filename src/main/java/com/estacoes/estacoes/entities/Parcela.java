package com.estacoes.estacoes.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int parcela;
    private LocalDate data;
    private BigDecimal amortizacao;
    private BigDecimal juros;
    @Column(name = "total_parcela")
    private BigDecimal totalParcela;
    @Column(name = "saldo_devedor")
    private BigDecimal saldoDevedor;
    private Integer emprestimo_id;

    public Parcela() {
    }

    public Parcela(Long id, int parcela, LocalDate data, BigDecimal amortizacao, BigDecimal juros, BigDecimal totalParcela, BigDecimal saldoDevedor, Integer emprestimo_id) {
        this.parcela = parcela;
        this.data = data;
        this.amortizacao = amortizacao;
        this.juros = juros;
        this.totalParcela = totalParcela;
        this.saldoDevedor = saldoDevedor;
        this.emprestimo_id = emprestimo_id;
        this.id = id;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getAmortizacao() {
        return amortizacao;
    }

    public void setAmortizacao(BigDecimal amortizacao) {
        this.amortizacao = amortizacao;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(BigDecimal saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public BigDecimal getTotalParcela() {
        return totalParcela;
    }

    public void setTotalParcela(BigDecimal totalParcela) {
        this.totalParcela = totalParcela;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmprestimo_id() {
        return emprestimo_id;
    }

    public void setEmprestimo_id(Integer emprestimo_id) {
        this.emprestimo_id = emprestimo_id;
    }
}
