package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoDetalhe;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplementoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complemento);
    }

    public void btnContinuarComplClick(View view) {
        //gravarLista(dados);
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }

    public void gravarLista(Map<String, List<TItemTela>> dados) {
/*        this.dados = dados;

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
                    item.setValor(this.dados.get(keys.get(i)).get(j).getCardapio_item().getValor());
                    id_item =  dbDao.inserirPedidoItem(item);

                    TPedidoDetalhe detalhe = new TPedidoDetalhe();
                    detalhe.setId_item(id_item);

                    TCardapioItem c = this.dados.get(keys.get(i)).get(j).getCardapio_item();
                    detalhe.setCardapio_item(c);
                    dbDao.inserirPedidoSubItem(detalhe);

                }
            }
        }
   */ }


}
