package com.estacoes.estacoes.entities;

import com.estacoes.estacoes.config.ComponentConfig;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String cpf_cnpj;
    private BigDecimal valor;
    private Integer parcelas;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    private String carencia;
    @Column(name = "taxa_mensal")
    private BigDecimal taxaMensal;
    @Column(name = "taxa_anual")
    private BigDecimal taxaAnual;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "emprestimo_id")
    private List<Parcela> parcelasResultado;

    public Emprestimo() {
    }

    public Emprestimo(Long id, String cpf_cnpj, BigDecimal valor, Integer parcelas, List<Parcela> parcelasResultado) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.valor = valor;
        this.parcelas = parcelas;

        this.parcelasResultado = parcelasResultado;
    }
    public void calculaParcelas() {
        this.taxaAnual = BigDecimal.valueOf(Double.parseDouble(ComponentConfig.getTAX_PATTERN()));
        this.taxaAnual  = this.taxaRisco(this.taxaAnual);
        this.taxaMensal = BigDecimal.valueOf((Math.pow((1 + (taxaAnual.doubleValue() / 100)), 1D / 12D)) - 1).setScale(6, RoundingMode.HALF_EVEN);
        BigDecimal.valueOf(1);
        BigDecimal um = new BigDecimal("1");
        BigDecimal operador = taxaMensal.add(um).pow(parcelas);

        parcelasResultado = new ArrayList<>();
        LocalDate data = LocalDate.now();
        var saldoDevedorAnterior = valor;
        parcelasResultado.add(new Parcela(null,0, data, null, null, null, valor, null));

        int contadorParcelasAdicionadas = 0;

        BigDecimal jurosCarencia;

        if (!this.carencia.equals("0")) {
            if (this.carencia.equals("30 dias") || this.carencia.equals("60 dias") || this.carencia.equals("90 dias")) {
                data = atualizaData(data);
                jurosCarencia = saldoDevedorAnterior.multiply(this.taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
                saldoDevedorAnterior =saldoDevedorAnterior.add(jurosCarencia);
                parcelasResultado.add(new Parcela(null, 0, data, null, jurosCarencia.multiply(BigDecimal.valueOf(-1)), null, saldoDevedorAnterior, null));
                contadorParcelasAdicionadas += 1;
                if (this.carencia.equals("60 dias") || this.carencia.equals("90 dias")) {
                    data = atualizaData(data);
                    jurosCarencia = saldoDevedorAnterior.multiply(this.taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
                    saldoDevedorAnterior = saldoDevedorAnterior.add(jurosCarencia);
                    parcelasResultado.add(new Parcela(null, 0, data, null, jurosCarencia.multiply(BigDecimal.valueOf(-1)), null, saldoDevedorAnterior, null));
                    contadorParcelasAdicionadas += 1;
                    if (this.carencia.equals("90 dias")) {
                        data = atualizaData(data);
                        jurosCarencia = saldoDevedorAnterior.multiply(this.taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
                        saldoDevedorAnterior = saldoDevedorAnterior.add(jurosCarencia);
                        parcelasResultado.add(new Parcela(null, 0, data, null, jurosCarencia.multiply(BigDecimal.valueOf(-1)), null, saldoDevedorAnterior, null));
                        contadorParcelasAdicionadas += 1;
                    }
                }
            }
        }
        BigDecimal parcelaPrecisa = saldoDevedorAnterior.multiply(((operador.multiply(taxaMensal)).divide(operador.subtract(um), RoundingMode.HALF_EVEN))).setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal parcela = parcelaPrecisa.setScale(2, RoundingMode.HALF_DOWN);

        for (int i = 1; i <= parcelas; i ++) {
            if (i != 1) {
                saldoDevedorAnterior = parcelasResultado.get(i + contadorParcelasAdicionadas -1).getSaldoDevedor();
            }
            var juros = saldoDevedorAnterior.multiply(taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
            var amortizacao = parcela.subtract(juros);
            if (i == 1) {
                juros = juros.add(saldoDevedorAnterior.subtract(valor));
                amortizacao = amortizacao.subtract(saldoDevedorAnterior.subtract(valor));
                saldoDevedorAnterior = saldoDevedorAnterior.subtract(saldoDevedorAnterior.subtract(valor));
            }
            if (i == parcelas) {
                data = atualizaData(data);
                parcelasResultado.add(new Parcela(null, i, data, saldoDevedorAnterior, juros, saldoDevedorAnterior.add(juros),saldoDevedorAnterior.subtract(saldoDevedorAnterior), null));
                break;
            }
            data = atualizaData(data);
            parcelasResultado.add(new Parcela(null, i, data, amortizacao, juros, parcela, saldoDevedorAnterior.subtract(amortizacao), null));
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

    public BigDecimal getTaxaAnual() {
        return taxaAnual;
    }

    public void setTaxaAnual(BigDecimal taxaAnual) {
        this.taxaAnual = taxaAnual;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCarencia() {
        return carencia;
    }

    public void setCarencia(String carencia) {
        this.carencia = carencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private BigDecimal taxaRisco(BigDecimal taxaNormal) {

        Integer idade = apuraIdade();

        System.out.println(idade);

        if(idade < 18) {
            throw new RuntimeException("Empréstimo exclusivo a partir de 18 anos.");
        } else if (idade < 19) {
           return taxaNormal.multiply(BigDecimal.valueOf(1.05));
        } else if (idade < 46) {
            return taxaNormal.multiply(BigDecimal.valueOf(1.02));
        } else if (idade < 69) {
            return taxaNormal.multiply(BigDecimal.valueOf(1.01));
        } else {
            return taxaNormal;
        }
    }

    private Integer apuraIdade() {
        LocalDate hoje = LocalDate.now();
        Integer idade = hoje.getYear() - this.dataNascimento.getYear();
        if (hoje.getMonthValue() <  this.dataNascimento.getMonthValue()) {
            idade -= 1;
        }
        if ((hoje.getMonthValue() == this.dataNascimento.getMonthValue()) && (hoje.getDayOfMonth() < this.dataNascimento.getDayOfMonth())) {
            idade -= 1;
        }
        return idade;
    }

    private LocalDate atualizaData(LocalDate data) {
        Integer ano = data.getYear();
        Integer mes = data.getMonthValue() + 1;
        if (mes == 13) {
            mes = 1;
            ano += 1;
        }
        return LocalDate.of(ano, mes, data.getDayOfMonth());
    }
}
