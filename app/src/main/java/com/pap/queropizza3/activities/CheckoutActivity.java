package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TCheckoutAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedidoItem;

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

        if (itens.size() == 0){
            Button btnFinalizarChk = (Button)findViewById(R.id.btnFinalizarChk);
            btnFinalizarChk.setVisibility(View.GONE);
        }

        ArrayAdapter<TPedidoItem> adapter = new TCheckoutAdapter(this, 0, itens);
        listView.setAdapter(adapter);
    }

    public void btnAdicionarItensClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }



}
