// http://blog.smartlogic.io/2013/07/09/organizing-your-android-development-code-structure
package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.utils.EnviarPedido;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (existeCliente()) {
                    Intent it = new Intent(getApplicationContext(), EstabelecimentoActivity.class);
                    startActivity(it);
                }
                else
                {
                    Intent it = new Intent(getApplicationContext(), ClienteActivity.class);
                    startActivity(it);
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

// chave api: AIzaSyCmCqojmk_KOO4B6ffzHwSDFElMnqUCz0w
