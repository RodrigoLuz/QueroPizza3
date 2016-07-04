package com.pap.queropizza3.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pap.queropizza3.R;

public class EstabelecimentoDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int e = 0;
        Bundle b = new Bundle();
        if(getIntent().hasExtra("estabelecimento")) {
            b = getIntent().getExtras();
        }
        e = b.getInt("estabelecimento");

        if (e == 0) {
            setContentView(R.layout.activity_estabelecimento_detalhe);
        } else {
            setContentView(R.layout.activity_estabelecimento_detalhe_2);
        }

    }
}
