package com.estacoes.estacoes.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_parametro")
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String description;

    @NotNull
    private String vl_parameter;

    @Override
    public String toString() {

        return "Taxa{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", vl_parameter='" + vl_parameter + '\'' +
                '}';
    }
}
