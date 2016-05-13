package com.pap.queropizza3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TamanhoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamanho);

    }

    public void btnTamanhoClick(View v){
        switch(v.getId()) {
            case R.id.btnTamanho1:

                break;
            case R.id.btnTamanho2:

                break;
            case R.id.btnTamanho3:

                break;
            case R.id.btnTamanho4:

                break;

            default:
                throw new RuntimeException("Unknow button ID");
        }
        Intent it = new Intent(this, MontaSaborActivity.class);
        startActivity(it);
    }

}
