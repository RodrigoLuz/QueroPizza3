package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TPedidoSubitem {

    int id_subitem;
    TCardapioItem cardapio_item;

    public int getId_subitem() {
        return id_subitem;
    }

    public void setId_subitem(int id_subitem) {
        this.id_subitem = id_subitem;
    }

    public TCardapioItem getCardapio_item() {
        return cardapio_item;
    }

    public void setCardapio_item(TCardapioItem cardapio_item) {
        this.cardapio_item = cardapio_item;
    }
}
