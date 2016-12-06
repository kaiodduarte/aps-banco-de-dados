package com.model.domain;

import java.time.LocalDate;

/**
 *
 * @author kds
 */
public class Compra {

    private LocalDate data;
    private int id;
    private int loja_id;
    private int produto_id;
    private double precoUnitario;
    private double precoTotal;
    private double quantidade;
    private String loja_nome;
    private String marca;
    private String medida;
    private String produto_nome;

    public Compra() {

    }

    public Compra(double quantidade, double precoUnitario, double precoTotal, LocalDate data) {
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.precoUnitario = precoUnitario;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getLoja_id() {
        return loja_id;
    }

    public void setLoja_id(int loja_id) {
        this.loja_id = loja_id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
