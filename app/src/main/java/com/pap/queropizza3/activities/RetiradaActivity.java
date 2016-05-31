package com.pap.queropizza3.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.utils.EnviarPedido;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/*
Seleciona a forma de retirada (delivery ou balcão)
Informa o valor de taxa de entrega, buscando no back, para delivery
 */

public class RetiradaActivity extends AppCompatActivity {

    Button btnAvancarRetirada;
    ProgressDialog progress;
    String url;
    Double taxa, distancia, tempo;
    RadioButton rdoDelivery;
    RadioGroup rgRetirada;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(progress != null){
                progress.dismiss();
            }

            switch (msg.what){
                case 1:
                      rdoDelivery.setText("Delivery (taxa de entrega R$ " + String.format( "%.2f", taxa) + ")");
                    break;
                case 2:
                    Toast.makeText(RetiradaActivity.this, "Erro de conexão", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirada);
        btnAvancarRetirada = (Button)findViewById(R.id.btnAvancarRetirada);
        rdoDelivery = (RadioButton)findViewById(R.id.rdoDelivery);
        rgRetirada = (RadioGroup)findViewById(R.id.rgRetirada);

        buscarValorEntrega("", buscarDestination());
    }

    // busca endereço do cliente cadastrado
    public String buscarDestination(){
        TCliente c = new TCliente();
        try {
            AppSQLDao dbDao;
            dbDao = new AppSQLDao(getApplicationContext());
            List<TCliente> clientes = dbDao.listaCliente();
            c = clientes.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String d = replaceNull(c.getEndereco()) + "," + replaceNull(c.getNumero()) + "," + replaceNull(c.getCidade());
        return d;
    }

    public static String replaceNull(String input) {
        return input == null ? "" : input;
    }

    public void btnAvancarRetiradaClick(View v){
        {
            AppSQLDao dbDao = new AppSQLDao(getApplicationContext());
            dbDao.apagarPedido();
            criarPedido();

            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
    }

    public void criarPedido(){
        int d;
        if ((rgRetirada.getCheckedRadioButtonId() == 0)){
            d = 1; // delivery
        }else{
            d = 0;
        }
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        TCliente c = dbDao.listaCliente().get(0);

        TPedido p = new TPedido();
        p.setDelivery(d);
        p.setTaxa(taxa);
        p.setCliente(c);

        Date dt = new Date();
        p.setDatahora(String.valueOf(dt.getTime()));
        int id_pedido = dbDao.inserirPedido(p);

        // grava id pedido gerado para utilizar posteriormente e controlar pedido
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id_pedido", id_pedido);
        editor.commit();
    }

    // busca valor da entrega no ws ApiEntrega
    public void buscarValorEntrega(final String origins, final String destinations) {
        progress = new ProgressDialog(RetiradaActivity.this);
        progress.setTitle("Calculando taxa de entrega");
        progress.setMessage("Aguarde");
        progress.show();
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = AndroidHttpClient.newInstance("HttpAndroid");

                // verificar se passa o endereço 100% correto no formato url
                URI uri = null;
                try {
                    uri = new URI("http","queropizzaweb.azurewebsites.net","/api/ApiEntrega/json?","destino=" + destinations,null);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                url = uri.toASCIIString();
                HttpGet get = new HttpGet(url);

                try {
                    HttpResponse response = client.execute(get);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        String dadosServidor = EntityUtils.toString(entity);
                        JSONObject objectRoot = new JSONObject(dadosServidor);

                        JSONArray arrayTaxa = objectRoot.getJSONArray("RotaPreco");
                        distancia = arrayTaxa.getJSONObject(0).getDouble("distancia");
                        tempo = arrayTaxa.getJSONObject(0).getDouble("tempo");
                        taxa = arrayTaxa.getJSONObject(0).getDouble("preco");

                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                } catch (JSONException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        });
        trd.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

}



