package com.pap.queropizza3.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TExpandableAdapter;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaSimplesActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;
    List<TPedidoItem> pedido = new ArrayList<TPedidoItem>();

    String [] tipos = {"Refrigerantes", "Vinhos", "Cervejas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_simples);

        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        Map<String, List<TPedidoItem>> dados =  new HashMap<String, List<TPedidoItem>>();
        dados.put("Refrigerantes", retornarBebidas());
        dados.put("Vinhos", retornarBebidas());

        lstvListaSimples.setAdapter(new TExpandableAdapter(dados));

        // cria o adapter para o layout do item
//        ArrayAdapter<String> adapter = new MyAdapter(this, R.layout.layout_item_lista_simples, bebidas);
        // passa o adapter para listview
//        lstvListaSimples.setAdapter(adapter);
    }

    public List<TPedidoItem> retornarBebidas(){
        Cursor cursor;
        List<TPedidoItem> bebidas = new ArrayList<TPedidoItem>();
        TPedidoItem p;

        p = new TPedidoItem();
        p.setQuantidade(0);
        p.setDescricao("Fanta");
        pedido.add(p);
        p = new TPedidoItem();
        p.setQuantidade(0);
        p.setDescricao("Guaraná");
        pedido.add(p);
        p = new TPedidoItem();
        p.setQuantidade(0);
        p.setDescricao("Coca Cola");
        pedido.add(p);
        p = new TPedidoItem();
        p.setQuantidade(0);
        p.setDescricao("Pepsi");
        pedido.add(p);

        return pedido;
    }

    public void btnContinuarClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }
}
