package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pap.queropizza3.R;

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

        Intent it = new Intent(this, SaborActivity.class);
        startActivity(it);
    }

}
