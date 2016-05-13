package com.pap.queropizza3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/*
Seleciona a forma de retirada (delivery ou balcão)
Informa o valor de taxa de entrega, buscando no back, para delivery
 */

public class RetiradaActivity extends AppCompatActivity {

    Button btnAvancarRetirada;
    ProgressDialog progress;
    String url, distancia;
    RadioButton rdoBalcao;

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
                     rdoBalcao.setText("Balcao R$ " + distancia);
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

        buscarValorEntrega("Curitiba", "Paranagua");
    }

    public void btnAvancarRetiradaClick(View v){
        {
            Intent it = new Intent(this, GrupoActivity.class);
            startActivity(it);
        }
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
                url = "https://maps.googleapis.com/maps/api/distancematrix/json?";
                url = url + "origins=" + origins + "&destinations=" + destinations;
                url = url + "&key=AIzaSyCmCqojmk_KOO4B6ffzHwSDFElMnqUCz0w";
                HttpGet get = new HttpGet(url);

                try {
                    HttpResponse response = client.execute(get);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        String dadosServidor = EntityUtils.toString(entity);
                        JSONObject objectRoot = new JSONObject(dadosServidor);

                        JSONArray arrayRows = objectRoot.getJSONArray("rows");
                        JSONObject objectRows = arrayRows.getJSONObject(0);

                        JSONArray arrayElements = objectRows.getJSONArray("elements");
                        JSONObject objectElements = arrayElements.getJSONObject(0);

                        JSONObject objectDistance = objectElements.getJSONObject("distance");

                        distancia = objectDistance.getString("value");

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
}



