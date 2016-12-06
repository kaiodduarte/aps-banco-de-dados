package com.model.domain;

/**
 *
 * @author kds
 */
public class Produto {

    private int id;
    private String nome;
    private String marca;
    private String medida;
    private int categoria_id;
    private String categoria_tipo;

    public Produto() {
    }

    public Produto(String nome, String marca, String medida, int categoria_id) {
        this.nome = nome;
        this.marca = marca;
        this.medida = medida;
        this.categoria_id = categoria_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public void print() {
        System.out.println(id + nome + categoria_tipo);
    }

    public String getCategoria_tipo() {
        return categoria_tipo;
    }

    public void setCategoria_tipo(String categoria_tipo) {
        this.categoria_tipo = categoria_tipo;
    }
}
