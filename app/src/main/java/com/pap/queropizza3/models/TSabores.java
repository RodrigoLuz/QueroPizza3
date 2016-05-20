package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 17/05/2016.
 */
public class TSabores {
    int idCardapioItem;
    String nome;
    String ingredientes;
    Double valor;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    boolean selecionado = false;

    public int getIdCardapioItem() {
        return idCardapioItem;
    }

    public void setIdCardapioItem(int idCardapioItem) {
        this.idCardapioItem = idCardapioItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
