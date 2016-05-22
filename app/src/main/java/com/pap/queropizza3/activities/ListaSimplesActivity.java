package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TExpandableAdapter;
import com.pap.queropizza3.models.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaSimplesActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_simples);

        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TCardapioGrupo g = new TCardapioGrupo();
        g.setCodGrupo(2); // grupo de bebidas, código está fixo, verificar

        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g);
        Map<String, List<TCardapioItem>> dados =  new HashMap<String, List<TCardapioItem>>();

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TCardapioItem> itens = dbDao.listaItem(subgrupos.get(i));
            dados.put(subgrupos.get(i).getNome(), itens);
        }
         lstvListaSimples.setAdapter(new TExpandableAdapter(dados));
    }

    public void btnContinuarClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }
}
