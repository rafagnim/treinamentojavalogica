package com.estacoes.estacoes.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf_cnpj;
    @NotNull
    @Column(name = "dt_vencimento")
    private LocalDate dataVencimento;

    @NotNull
    private BigDecimal vl_contrato;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contrato_id")
    public List<ItemContrato> itensContrato;

    public Contrato() {
    }

    public Contrato(Long id, String cpf_cnpj, LocalDate dataVencimento, BigDecimal vl_contrato, List<ItemContrato> itensContrato) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.dataVencimento = dataVencimento;
        this.vl_contrato = vl_contrato;
        this.itensContrato = itensContrato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public BigDecimal getVl_contrato() {
        return vl_contrato;
    }

    public void setVl_contrato(BigDecimal vl_contrato) {
        this.vl_contrato = vl_contrato;
    }

    public List<ItemContrato> getItensContrato() {
        return itensContrato;
    }

    public void setItensContrato(List<ItemContrato> itensContrato) {
        this.itensContrato = itensContrato;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
