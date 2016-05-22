// http://blog.smartlogic.io/2013/07/09/organizing-your-android-development-code-structure
package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.AppSQLDao;
import com.pap.queropizza3.models.TCliente;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnTesteClick(View v){
        if (existeCliente()) {
            Intent it = new Intent(this, EstabelecimentoActivity.class);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(this, ClienteActivity.class);
            startActivity(it);
        }
    }

    public boolean existeCliente(){
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<TCliente> clientes = dbDao.listaCliente();
        if (clientes.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

}

// chave api: AIzaSyCmCqojmk_KOO4B6ffzHwSDFElMnqUCz0w
