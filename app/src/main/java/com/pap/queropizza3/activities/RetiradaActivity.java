package com.pap.queropizza3.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.pap.queropizza3.models.AppSQLDao;
import com.pap.queropizza3.models.InternalStorage;
import com.pap.queropizza3.models.TCliente;

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
import java.util.List;

/*
Seleciona a forma de retirada (delivery ou balcão)
Informa o valor de taxa de entrega, buscando no back, para delivery
 */

public class RetiradaActivity extends AppCompatActivity {

    Button btnAvancarRetirada;
    ProgressDialog progress;
    String url, distancia, valor, tempo;
    RadioButton rdoBalcao;
    RadioGroup rgRetirada;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if(progress != null){
//                progress.dismiss();
//            }
//
            switch (msg.what){
                case 1:
                     rdoBalcao.setText("Balcao R$ " + valor);
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
        rdoBalcao = (RadioButton)findViewById(R.id.rdoBalcao);
        rgRetirada = (RadioGroup)findViewById(R.id.rgRetirada);

        buscarValorEntrega("", buscarDestination());
    }

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
            Boolean d = (rgRetirada.getCheckedRadioButtonId() == 0);
            //salvarRetirada((float) 10.00, d);
            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
    }

    public int criarPedido(){

        return 1;
    }

    public void buscarValorEntrega(final String origins, final String destinations) {
//        progress = new ProgressDialog(RetiradaActivity.this);
//        progress.setTitle("Conectando");
//        progress.setMessage("Aguarde");
//        progress.show();  só se levar tempo para acessar WS
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
                        distancia = arrayTaxa.getJSONObject(0).getString("distancia");
                        tempo = arrayTaxa.getJSONObject(0).getString("tempo");
                        valor = arrayTaxa.getJSONObject(0).getString("preco");

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



