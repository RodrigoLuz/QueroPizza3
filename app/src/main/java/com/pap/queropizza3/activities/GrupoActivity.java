package com.pap.queropizza3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pap.queropizza3.R;

public class GrupoActivity extends AppCompatActivity {

    Button btnGrupo1, btnGrupo2, btnGrupo3, btnGrupo4, btnGrupo5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        btnGrupo1 = (Button)findViewById(R.id.btnGrupo1);
//        btnGrupo2 = (Button)findViewById(R.id.btnGrupo2);
//        btnGrupo3 = (Button)findViewById(R.id.btnGrupo3);
//        btnGrupo4 = (Button)findViewById(R.id.btnGrupo4);
        btnGrupo5 = (Button)findViewById(R.id.btnGrupo5);
    }

    public void btnGrupo1Click(View v){
        {
            Intent it = new Intent(this, TamanhoActivity.class);
            startActivity(it);
        }
    }

    public void btnGrupo2Click(View v){
        {
            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
    }

    public void btnGrupo3Click(View v){
        {
            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
    }

    public void btnGrupo4Click(View v){
        {
            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
    }

    public void btnGrupo5Click(View v){
        {
            Intent it = new Intent(this, ListaSimplesActivity.class);
            startActivity(it);
        }
    }

    public void btnFinalizarClick(View view) {
        Intent it = new Intent(this, CheckoutActivity.class);
        startActivity(it);
    }
}
