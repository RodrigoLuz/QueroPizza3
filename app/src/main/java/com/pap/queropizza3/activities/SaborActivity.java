package com.pap.queropizza3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TCheckAdapter;
import com.pap.queropizza3.adapters.TExpandableAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaborActivity extends AppCompatActivity {

    List<TItemTela> sabores = new ArrayList<TItemTela>();
    List<TItemTela> saboresSelecionados = new ArrayList<TItemTela>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabor);

        ListView lstvSabores;
        lstvSabores = (ListView)findViewById(R.id.lstvSabores);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getBaseContext());

        TCardapioGrupo g = dbDao.buscarGrupo(1);
        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g);

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TItemTela> itens = dbDao.listaItensPorSubGrupo(subgrupos.get(i).getId_subgrupo());

            for(int j = 0 ; j < itens.size(); j++) {
                sabores.add(itens.get(j));
            }
        }

        ArrayAdapter<TItemTela> adapter = new TCheckAdapter(this, 0, sabores);
        lstvSabores.setAdapter(adapter);
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
