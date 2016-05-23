package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedidoItem;

public class TamanhoActivity extends AppCompatActivity {

    int tamanho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamanho);

    }

    public void btnTamanhoClick(View v){
        switch(v.getId()) {
            case R.id.btnTamanho1:
                tamanho = 1;
                break;
            case R.id.btnTamanho2:
                tamanho = 2;
                break;
            case R.id.btnTamanho3:
                tamanho = 3;
                break;
            case R.id.btnTamanho4:
                tamanho = 4;
                break;
            default:
                throw new RuntimeException("Opção inválida");
        }

        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo errado ...

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        TPedidoItem p = new TPedidoItem();
        p.setId_pedido(id_pedido);
        p.setTamanho(tamanho);
        p.setQuantidade(1);
        int id_pedido_item = dbDao.inserirPedidoItem(p);

        Intent it = new Intent(this, MontaSaborActivity.class);
        startActivity(it);
    }

}
