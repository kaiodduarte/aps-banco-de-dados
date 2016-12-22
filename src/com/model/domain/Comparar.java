package com.model.domain;

import java.time.LocalDate;

/**
 *
 * @author kds
 */
public class Comparar {

    private String loja_nome;
    private String produto_nome;
    private String produto_marca;
    private double preco_unitario;
    private LocalDate data;

    public Comparar() {
    }

    public String getLoja_nome() {
        return loja_nome;
    }

    public void setLoja_nome(String loja_nome) {
        this.loja_nome = loja_nome;
    }

    public String getProduto_nome() {
        return produto_nome;
    }

    public void setProduto_nome(String produto_nome) {
        this.produto_nome = produto_nome;
    }

    public double getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(double preco_unitario) {
        this.preco_unitario = preco_unitario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getProduto_marca() {
        return produto_marca;
    }

    public void setProduto_marca(String produto_marca) {
        this.produto_marca = produto_marca;
    }
}
