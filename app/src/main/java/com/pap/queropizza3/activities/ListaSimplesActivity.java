package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TExpandableAdapter;
import com.pap.queropizza3.models.AppSQLDao;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaSimplesActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;
    List<TPedidoItem> pedido = new ArrayList<TPedidoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_simples);

        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(null);
        Map<String, List<TCardapioItem>> dados =  new HashMap<String, List<TCardapioItem>>();

        for(int i = 0 ; i < subgrupos.size(); i++){
            //erro, não busca itens
            List<TCardapioItem> itens = dbDao.listaItem(subgrupos.get(i));
      //      List<TCardapioItem> itens = dbDao.listaItem(null);
            dados.put(subgrupos.get(i).getNome(), itens);
        }

         lstvListaSimples.setAdapter(new TExpandableAdapter(dados));
    }

    public List<TPedidoItem> retornarBebidas(){
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
