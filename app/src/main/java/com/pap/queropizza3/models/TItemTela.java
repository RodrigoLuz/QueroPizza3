package com.pap.queropizza3.models;

import java.io.Serializable;

/**
 * Created by Rodrigo on 17/05/2016.
 */
public class TItemTela implements Serializable{
    int idCardapioItem;
    TCardapioItem cardapio_item;
    boolean selecionado = false;
    int quantidade = 0;

    public int getIdCardapioItem() {
        return idCardapioItem;
    }

    public void setIdCardapioItem(int idCardapioItem) {
        this.idCardapioItem = idCardapioItem;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public TCardapioItem getCardapio_item() {
        return cardapio_item;
    }

    public void setCardapio_item(TCardapioItem cardapio_item) {
        this.cardapio_item = cardapio_item;
    }
}
