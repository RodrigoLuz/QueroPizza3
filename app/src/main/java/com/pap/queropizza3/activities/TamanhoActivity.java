package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedido;

public class TamanhoActivity extends AppCompatActivity {

    int tamanho;
    Button btnTamanho1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamanho);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo errado
        TPedido p;
        p = dbDao.buscarPedido(id_pedido);

        if (p.isDelivery() == 1 ){
            btnTamanho1 = (Button)findViewById(R.id.btnTamanho1);
            btnTamanho1.setVisibility(View.GONE);
        }

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

        Intent it = new Intent(this, SaborActivity.class);
        startActivity(it);
    }

}
