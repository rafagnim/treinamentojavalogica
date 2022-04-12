package com.estacoes.estacoes.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tb_contrato_item")
public class ItemContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String id_duplicata;
    @Column(name = "dt_vencimento")
    private LocalDate dataVencimento;
    private Double vl_duplicata;
    private Integer contrato_id;

    public ItemContrato() {
    }

    public ItemContrato(Integer id, String id_duplicata, LocalDate dataVencimento, Double vl_duplicata, Integer contrato_id) {
        this.id = id;
        this.id_duplicata = id_duplicata;
        this.dataVencimento = dataVencimento;
        this.vl_duplicata = vl_duplicata;
        this.contrato_id = contrato_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_duplicata() {
        return id_duplicata;
    }

    public void setId_duplicata(String id_duplicata) {
        this.id_duplicata = id_duplicata;
    }

    public Double getVl_duplicata() {
        return vl_duplicata;
    }

    public void setVl_duplicata(Double vl_duplicata) {
        this.vl_duplicata = vl_duplicata;
    }

    public Integer getContrato_id() {
        return contrato_id;
    }

    public void setContrato_id(Integer contrato_id) {
        this.contrato_id = contrato_id;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
