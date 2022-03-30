package com.estacoes.estacoes.entities;

import javax.persistence.*;

@Entity
@Table(name = "tb_contrato_item")
public class ItemContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String id_duplicata;
    private Double vl_duplicata;
    @ManyToOne
    private Contrato c;

    public ItemContrato() {
    }

    public ItemContrato(Integer id, String id_duplicata, Double vl_duplicata, Contrato c) {
        this.id = id;
        this.id_duplicata = id_duplicata;
        this.vl_duplicata = vl_duplicata;
        this.c = c;
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

    public Contrato getC() {
        return c;
    }

    public void setC(Contrato c) {
        this.c = c;
    }
}
