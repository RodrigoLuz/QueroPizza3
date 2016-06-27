// http://blog.smartlogic.io/2013/07/09/organizing-your-android-development-code-structure
// http://www.vogella.com/tutorials/AndroidActionBar/article.html
// https://developer.android.com/training/appbar/index.html
// http://www.androidhive.info/2013/11/android-working-with-action-bar/
// https://www.sitepoint.com/better-user-interfaces-android-action-bar/
// https://material.google.com/components/bottom-navigation.html#bottom-navigation-specs
// https://material.google.com/layout/structure.html#

package com.pap.queropizza3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCliente;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // se não existir cadastro de usuário chama o cadastro, senão estabelecimento
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buscaCliente() == null) {
                    Intent it = new Intent(getApplicationContext(), ClienteActivity.class);
                    startActivity(it);
                }
                else
                {
                    Intent it = new Intent(getApplicationContext(), EstabelecimentoActivity.class);
                    startActivity(it);
                }
            }
        });

    }

    public TCliente buscaCliente(){
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<TCliente> clientes = dbDao.listaCliente();
        if (clientes.isEmpty()){
            return null;
        }
        else{
            return clientes.get(0);
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
            Intent it = new Intent(this, ClienteActivity.class);
            TCliente c = buscaCliente();  // se já existir cliente envia para preencher tela
            if (c != null){
                it.putExtra("cliente", c);
            }
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

// chave api: AIzaSyCmCqojmk_KOO4B6ffzHwSDFElMnqUCz0w
