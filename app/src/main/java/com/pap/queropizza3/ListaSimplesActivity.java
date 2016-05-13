package com.pap.queropizza3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaSimplesActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;
    List<TPedidoCardapio> pedido = new ArrayList<TPedidoCardapio>();

    String [] tipos = {"Refrigerantes", "Vinhos", "Cervejas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_simples);

        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        Map<String, List<TPedidoCardapio>> dados =  new HashMap<String, List<TPedidoCardapio>>();
        dados.put("Refrigerantes", retornarBebidas());
        dados.put("Vinhos", retornarBebidas());

        lstvListaSimples.setAdapter(new TExpandableAdapter(dados));

        // cria o adapter para o layout do item
//        ArrayAdapter<String> adapter = new MyAdapter(this, R.layout.layout_item_lista_simples, bebidas);
        // passa o adapter para listview
//        lstvListaSimples.setAdapter(adapter);
    }

    public List<TPedidoCardapio> retornarBebidas(){
        Cursor cursor;
        List<TPedidoCardapio> bebidas = new ArrayList<TPedidoCardapio>();
        TPedidoCardapio p;

        p = new TPedidoCardapio();
        p.setQuantidade(0);
        p.setDescricao("Fanta");
        pedido.add(p);
        p = new TPedidoCardapio();
        p.setQuantidade(0);
        p.setDescricao("Guaraná");
        pedido.add(p);
        p = new TPedidoCardapio();
        p.setQuantidade(0);
        p.setDescricao("Coca Cola");
        pedido.add(p);
        p = new TPedidoCardapio();
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
