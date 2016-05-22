package com.pap.queropizza3.models;

import java.util.List;

/**
 * Created by Rodrigo on 22/04/2016.
 */
public class TPedidoItem {

    int quantidade;
    String descricao;
    float valor;
    private List<TPedidoSubitem> subitens;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public List<TPedidoSubitem> getSubitens() {
        return subitens;
    }

    public void setSubitens(List<TPedidoSubitem> subitens) {
        this.subitens = subitens;
    }
}
