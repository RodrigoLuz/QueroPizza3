package com.pap.queropizza3.models;

import java.util.List;

/**
 * Created by Rodrigo on 22/04/2016.
 */
public class TPedidoItem {

    int id_item;
    int id_pedido;
    int quantidade;
    float valor;
    int tamanho;
    String observacao;
    private List<TPedidoSubitem> subitens;

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<TPedidoSubitem> getSubitens() {
        return subitens;
    }

    public void setSubitens(List<TPedidoSubitem> subitens) {
        this.subitens = subitens;
    }
}
