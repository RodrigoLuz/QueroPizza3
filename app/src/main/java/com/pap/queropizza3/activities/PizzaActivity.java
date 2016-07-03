package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
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

public class PizzaActivity extends AppCompatActivity {

    ExpandableListView lstvSabores;
    Map<String, List<TItemTela>> dados =  new HashMap<String, List<TItemTela>>();
    int tamanho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pizzas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PizzaActivity.this, ComplementoActivity.class);

                List<TItemTela> saboresSelecionados = gravarLista(dados);
                if (saboresSelecionados.size() > 0 ){
                    Bundle bundleObject = new Bundle();
                    bundleObject.putSerializable("saboresSelecionados", (Serializable) saboresSelecionados);
                    it.putExtras(bundleObject);
                }

                Bundle bundle = new Bundle();
                bundle.putInt("tamanho", tamanho);
                it.putExtras(bundle);

                startActivity(it);
            }
        });

        if(getIntent().hasExtra("tamanho")) {
            Bundle b = new Bundle();
            b = getIntent().getExtras();
            tamanho = b.getInt("tamanho");
        }

        lstvSabores = (ExpandableListView)findViewById(R.id.lstvSabores);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TCardapioGrupo g = dbDao.buscarGrupo(1); //g.setCod_grupo(2); // grupo de bebidas, código está fixo, verificar
        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g); // pega sibgrupos de bebida // não pode listar pelo id pois é variável, tem que ser pelo cod do servidor api

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TItemTela> itens = dbDao.listaItensPorSubGrupo(subgrupos.get(i));  // busca itens dos subgrupos
            dados.put(subgrupos.get(i).getNome(), itens);  // insere chave (= cabeçalho = subgrupo)
        }
        lstvSabores.setAdapter(new TPizzaAdapter(dados));
    }

    public List<TItemTela> gravarLista(Map<String, List<TItemTela>> dados) {
        this.dados = dados;

        List<TItemTela> saboresSelecionados = new ArrayList<TItemTela>();
        List<String> keys = new ArrayList<String>(this.dados.keySet());

        for(int i = 0 ; i < keys.size(); i++) {

            for(int j = 0 ; j < this.dados.get(keys.get(i)).size(); j++) {
                if (this.dados.get(keys.get(i)).get(j).isSelecionado()) {
                    TItemTela itemTela = this.dados.get(keys.get(i)).get(j);
                    saboresSelecionados.add(itemTela);
                 }
            }
        }
        return saboresSelecionados;
    }

}
