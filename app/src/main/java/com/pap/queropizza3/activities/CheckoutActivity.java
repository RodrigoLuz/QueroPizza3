package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TCheckoutAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.models.TPedidoItem;
import com.pap.queropizza3.utils.EnviarPedido;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    List<TPedidoItem> itens = new ArrayList<TPedidoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ListView listView = (ListView)findViewById(R.id.lstvCheckout);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        itens = dbDao.listaTodosPedidoItem();

        ArrayAdapter<TPedidoItem> adapter = new TCheckoutAdapter(this, 0, itens);
        listView.setAdapter(adapter);
    }

    public void btnFinalizarChkClick(View view) {
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo
        TPedido p;
        p = dbDao.buscarPedido(id_pedido);

        EnviarPedido e = new EnviarPedido();
        e.envia(p, getBaseContext());
    }

    public void btnAdicionarItensClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }

}
