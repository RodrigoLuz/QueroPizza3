package com.pap.queropizza3.utils;

import android.util.Log;

import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.models.TPedidoDetalhe;
import com.pap.queropizza3.models.TPedidoItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Rodrigo on 29/05/2016.
 */
public class JsonPedido {
    public static String toJSon(TPedido pedido) {

        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("delivery", true); //pedido.isDelivery());
            jsonObject.put("taxa", pedido.getTaxa());
            jsonObject.put("datahora", pedido.getDatahora());
            jsonObject.put("nome", pedido.getCliente().getNome());
            jsonObject.put("cep", pedido.getCliente().getCep());
            jsonObject.put("endereco", pedido.getCliente().getEndereco());
            jsonObject.put("numero", pedido.getCliente().getNumero());
            jsonObject.put("complemento", pedido.getCliente().getComplemento());
            jsonObject.put("bairro", pedido.getCliente().getBairro());
            jsonObject.put("cidade", pedido.getCliente().getCidade());
            jsonObject.put("uf", pedido.getCliente().getUf());
            jsonObject.put("email", pedido.getCliente().getEmail());
            jsonObject.put("telefone", pedido.getCliente().getTelefone());

            // In this case we need a json array to hold the java list
            JSONArray jsonPedidoItem = new JSONArray();

            for (TPedidoItem pedidoitem : pedido.getItens() ) {
                JSONObject itemObj = new JSONObject();
                itemObj.put("id_item", pedidoitem.getId_item());
                itemObj.put("quantidade", pedidoitem.getQuantidade());
                itemObj.put("valor", pedidoitem.getValor());
                itemObj.put("tamanho", pedidoitem.getTamanho());
                itemObj.put("observacao", pedidoitem.getObservacao());
                JSONArray jsonPedidoDetalhe = new JSONArray();
                for (TPedidoDetalhe pedidodetalhe : pedidoitem.getSubitens() ) {
                    JSONObject detalheObj = new JSONObject();
                    detalheObj.put("id_detalhe", pedidodetalhe.getId_detalhe());
                    detalheObj.put("id_cardapio", pedidodetalhe.getCardapio_item().getCod_item());
                    detalheObj.put("valor", pedidodetalhe.getCardapio_item().getValor());
                    jsonPedidoDetalhe.put(detalheObj);
                }
                itemObj.put("detalhe", jsonPedidoDetalhe);
                jsonPedidoItem.put(itemObj);
            }
            jsonObject.put("item", jsonPedidoItem);

            return jsonObject.toString();

        }
            catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
}
