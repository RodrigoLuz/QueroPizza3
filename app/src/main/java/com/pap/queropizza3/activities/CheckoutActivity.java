package com.pap.queropizza3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.utils.EnviarPedido;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }


    public void btnVai(View view) {

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        TPedido p;
        p = dbDao.buscarPedido(1);

        EnviarPedido e = new EnviarPedido();
        e.envia(p);

    }
}
