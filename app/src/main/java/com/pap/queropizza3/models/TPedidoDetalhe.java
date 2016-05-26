package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TPedidoDetalhe {

    int id_detalhe;
    int id_item;
    TCardapioItem cardapio_item;
    float valor;

    public int getId_detalhe() {
        return id_detalhe;
    }

    public void setId_detalhe(int id_detalhe) {
        this.id_detalhe = id_detalhe;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public TCardapioItem getCardapio_item() {
        return cardapio_item;
    }

    public void setCardapio_item(TCardapioItem cardapio_item) {
        this.cardapio_item = cardapio_item;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
