package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TBebidaAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoDetalhe;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// http://stackoverflow.com/questions/34259986/how-to-save-listview-in-android-and-fetch-it-back-when-required
// http://stackoverflow.com/questions/4777003/how-do-i-load-and-save-listview-items-to-an-sqlitedatabase
 //https://www.youtube.com/watch?v=PA4A9IesyCg

public class BebidaActivity extends AppCompatActivity {

    ExpandableListView lstvListaSimples;
    Map<String, List<TItemTela>> dados =  new HashMap<String, List<TItemTela>>();
    // private static final String TAG = "Quant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bebidas);
        lstvListaSimples = (ExpandableListView)findViewById(R.id.lstvListaSimples);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TCardapioGrupo g = dbDao.buscarGrupo(2); //g.setCod_grupo(2); // grupo de bebidas, código está fixo, verificar
        List<TCardapioSubGrupo> subgrupos = dbDao.listaSubGrupo(g); // pega sibgrupos de bebida // não pode listar pelo id pois é variável, tem que ser pelo cod do servidor api

        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TItemTela> itens = dbDao.listaItensPorSubGrupo(subgrupos.get(i));  // busca itens dos subgrupos
            dados.put(subgrupos.get(i).getNome(), itens);  // insere chave (= cabeçalho = subgrupo)
        }
         lstvListaSimples.setAdapter(new TBebidaAdapter(dados));
    }

    public void btnContinuarClick(View view) {
        gravarLista(dados);
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }

    public void gravarLista(Map<String, List<TItemTela>> dados) {
        this.dados = dados;

        //Restaura id_pedido gravado
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1);
        int id_item;

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<String> keys = new ArrayList<String>(this.dados.keySet());

        for(int i = 0 ; i < keys.size(); i++) {

            for(int j = 0 ; j < this.dados.get(keys.get(i)).size(); j++) {
                int quant = this.dados.get(keys.get(i)).get(j).getQuantidade();
                if (quant > 0) {
                    TPedidoItem item = new TPedidoItem();
                    item.setId_pedido(id_pedido);
                    item.setQuantidade(quant);

                    TCardapioItem cardapioItem = this.dados.get(keys.get(i)).get(j).getCardapio_item();

                    item.setValor(cardapioItem.getValor());
                    item.setGrupo(cardapioItem.getSubgrupo().getGrupo().getCod_grupo());
                    item.setSubgrupo(cardapioItem.getSubgrupo().getCod_subgrupo());
                    id_item =  dbDao.inserirPedidoItem(item);

                    TPedidoDetalhe detalhe = new TPedidoDetalhe();
                    detalhe.setId_item(id_item);

                    TCardapioItem c = this.dados.get(keys.get(i)).get(j).getCardapio_item();
                    detalhe.setCardapio_item(c);
                    dbDao.inserirPedidoSubItem(detalhe);

                }
            }
        }
    }

}
