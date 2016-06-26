package com.pap.queropizza3.models;

import java.util.List;

/**
 * Created by Rodrigo on 21/05/2016.
 */
public class TPedido {
    private int id_pedido;
    private int delivery;
    private Integer f_pagamento;
    private Double troco_para;
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

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public Integer getF_pagamento() {
        return f_pagamento;
    }

    public void setF_pagamento(Integer f_pagamento) {
        this.f_pagamento = f_pagamento;
    }

    public Double getTroco_para() {
        return troco_para;
    }

    public void setTroco_para(Double troco_para) {
        this.troco_para = troco_para;
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
