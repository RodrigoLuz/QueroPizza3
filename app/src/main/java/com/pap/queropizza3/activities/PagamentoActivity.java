package com.pap.queropizza3.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.utils.EnviarPedido;

import java.math.BigDecimal;

public class PagamentoActivity extends AppCompatActivity {

    private static final String PAG_SEGURO_PACKAGE_NAME = "br.com.uol.ps";
    private static final String PAG_SEGURO_CLASS_NAME = "br.com.uol.ps.app.MainActivity";
    private static final String FLAG_APP_PAYMENT_VALUE = "FLAG_APP_PAYMENT_VALUE";
    private static final int REQUEST_CODE = 3016; // Pode ser o número de sua escolha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
    }

    private boolean checkIfAppIsInstalled() {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(PAG_SEGURO_PACKAGE_NAME,
                    PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }


    public void pagSeguroClick(View view) {
        if (checkIfAppIsInstalled()) {
            BigDecimal paymentValue = new BigDecimal("10.99");
            Intent it = new Intent(Intent.ACTION_MAIN);
            it.setClassName(PAG_SEGURO_PACKAGE_NAME, PAG_SEGURO_CLASS_NAME); // Intent que será chamada.
            it.putExtra(FLAG_APP_PAYMENT_VALUE, paymentValue); // Valor da venda.
            try {
                startActivityForResult(it, REQUEST_CODE); // Chama o aplicativo do PagSeguro.
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Erro ao abrir PagSeguro", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Aplicativo PagSeguro não instalado", Toast.LENGTH_SHORT).show();
        }
    }


    public void btnFinalizarChkClick(View view) {
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo errado
        TPedido p;
        p = dbDao.buscarPedido(id_pedido);

        EnviarPedido e = new EnviarPedido();
        e.envia(p, getBaseContext());
    }

}