package com.pap.queropizza3.models;

import java.util.List;

/**
 * Created by Rodrigo on 21/05/2016.
 */
public class TPedido {
    private int id_pedido;
    private int delivery;
    private Double taxa;
    private String datahora;
    private TCliente cliente;
    private List<TPedidoItem> itens;

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public TCliente getCliente() {
        return cliente;
    }

    public void setCliente(TCliente cliente) {
        this.cliente = cliente;
    }

    public int isDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public List<TPedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<TPedidoItem> itens) {
        this.itens = itens;
    }
}
