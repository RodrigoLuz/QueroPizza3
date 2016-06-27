package com.pap.queropizza3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TBebidaAdapter;
import com.pap.queropizza3.adapters.TCheckAdapter;
import com.pap.queropizza3.adapters.TPizzaAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TItemTela;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaborActivity extends AppCompatActivity {

    List<TItemTela> sabores = new ArrayList<TItemTela>();
    List<TItemTela> saboresSelecionados = new ArrayList<TItemTela>();

    ExpandableListView lstvSabores;
    Map<String, List<TItemTela>> dados =  new HashMap<String, List<TItemTela>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabor);

        lstvSabores = (ExpandableListView)findViewById(R.id.lstvSabores);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TCardapioGrupo g = dbDao.buscarGrupo(1); //g.setCod_grupo(2); // grupo de bebidas, código está fixo, verificar
        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g); // pega sibgrupos de bebida // não pode listar pelo id pois é variável, tem que ser pelo cod do servidor api

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TItemTela> itens = dbDao.listaItensPorSubGrupo(subgrupos.get(i).getId_subgrupo());  // busca itens dos subgrupos
            dados.put(subgrupos.get(i).getNome(), itens);  // insere chave (= cabeçalho = subgrupo)
        }
        lstvSabores.setAdapter(new TPizzaAdapter(dados));
    }

    public void btnContinuarSabClick(View view) {
        Intent it = new Intent(this, ComplementoActivity.class);

        // por enquanto estou fazendo aqui depois
        // saboresSelecionados tem que ser preenchido ao selecionar

        for(int j = 0 ; j < sabores.size(); j++) {
            if (sabores.get(j).isSelecionado())
            saboresSelecionados.add(sabores.get(j));
        }

        if (saboresSelecionados.size() > 0 ){
            Bundle bundleObject = new Bundle();
            bundleObject.putSerializable("saboresSelecionados", (Serializable) saboresSelecionados);
            it.putExtras(bundleObject);
        }

        startActivity(it);
    }
}
