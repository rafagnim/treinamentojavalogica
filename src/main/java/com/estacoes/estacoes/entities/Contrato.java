package com.estacoes.estacoes.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cpf_cnpj;
    @NotNull
    private Double vl_contrato;
    @OneToMany
    private List<Contrato> itens;

    public Contrato() {
    }

    public Contrato(Integer id, String cpf_cnpj, Double vl_contrato) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.vl_contrato = vl_contrato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public Double getVl_contrato() {
        return vl_contrato;
    }

    public void setVl_contrato(Double vl_contrato) {
        this.vl_contrato = vl_contrato;
    }

    public List<Contrato> getItens() {
        return itens;
    }

    public void setItens(List<Contrato> itens) {
        this.itens = itens;
    }
}
