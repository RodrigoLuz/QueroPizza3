package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TExpandableAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TItemTela;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// http://stackoverflow.com/questions/34259986/how-to-save-listview-in-android-and-fetch-it-back-when-required
// http://stackoverflow.com/questions/4777003/how-do-i-load-and-save-listview-items-to-an-sqlitedatabase

public class ListaSimplesActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;
    Map<String, List<TItemTela>> dados =  new HashMap<String, List<TItemTela>>();
    private static final String TAG = "Quant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_simples);

        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TCardapioGrupo g = new TCardapioGrupo();
        g.setCodGrupo(2); // grupo de bebidas, código está fixo, verificar

        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g); // pega sibgrupos de bebida

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TItemTela> itens = dbDao.retornarItensPorSubGrupo(subgrupos.get(i).getCodSubGrupo());
            dados.put(subgrupos.get(i).getNome(), itens);
        }
         lstvListaSimples.setAdapter(new TExpandableAdapter(dados));
    }

    public void btnContinuarClick(View view) {
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<String> keys = new ArrayList<String>(dados.keySet());

        for(int i = 0 ; i < keys.size(); i++) {

            for(int j = 0 ; j < dados.get(keys.get(i)).size(); j++) {
            Log.v(TAG, String.valueOf(dados.get(keys.get(i)).get(j).getQuantidade()));
            }
        }
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }
}
