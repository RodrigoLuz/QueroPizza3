// https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html

package com.pap.queropizza3.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TPedido;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntregaActivity extends AppCompatActivity {

    ListView list;
    List<String> entrega = new ArrayList<String>();// {"Delivery", "Balcão"};
    List<String> info = new ArrayList<String>();// {"Delivery", "Balcão"};
    List<Integer> imageId = new ArrayList<>(); //  {R.drawable.ic_action_mais, R.drawable.ic_action_menos};
    String urlWeb = "queropizza.azurewebsites.net";
    AppSQLDao dbDao;
    Double taxa = 0.00, distancia, tempo;
    ProgressDialog progress;
    CustomList adapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(progress != null){
                progress.dismiss();
            }

            switch (msg.what){
                case 1:
                    info.set(1, "Taxa de entrega: R$" + String.format( "%.2f", taxa) + ",  tempo estimado: " + Math.round(tempo / 60) + "min") ;
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(EntregaActivity.this, "Erro de conexão", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Forma de entrega");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbDao = new AppSQLDao(getApplicationContext());

        adapter = new CustomList(this, entrega, info, imageId);
        list=(ListView)findViewById(R.id.lstvEntrega);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        Bundle b = new Bundle();
        if(getIntent().hasExtra("delivery")) {
            b = getIntent().getExtras();
        }

        if (b.getBoolean("delivery")){
            buscarValorEntrega("", buscarEnderecoUsuario());
            entrega.add("Balcão");
            imageId.add(R.drawable.pizza_icon);

            entrega.add("Delivery");
            imageId.add(R.drawable.pizza_delivery);

            info.add("");
            info.add("");

        } else {
            entrega.add("Balcão");
            imageId.add(R.drawable.pizza_icon);
            info.add("(Delivery indisponível para esta unidade no momento)");
        }

    }

    public class CustomList extends ArrayAdapter<String>{

        private final Activity context;
        private final List<String> entrega;
        private final List<String> info;
        private final List<Integer> imageId;
        public CustomList(Activity context, List<String> entrega, List<String> info, List<Integer> imageId) {
            super(context, R.layout.layout_item_entrega, entrega);
            this.context = context;
            this.entrega = entrega;
            this.info = info;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.layout_item_entrega, null, true);

            TextView txtvNome = (TextView) rowView.findViewById(R.id.txtvNome);
            TextView txtvInfo1 = (TextView) rowView.findViewById(R.id.txtvInfo1);
            ImageView imgView = (ImageView) rowView.findViewById(R.id.imgView);

            txtvNome.setText(entrega.get(position));
            txtvInfo1.setText(info.get(position));
            imgView.setImageResource(imageId.get(position));
            return rowView;
        }
    }

    // busca endereço do cliente cadastrado
    public String buscarEnderecoUsuario(){
        TCliente c = new TCliente();
        try {
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

    public void criarPedido(int tipo){
        TCliente c = dbDao.listaCliente().get(0);

        TPedido p = new TPedido();
        p.setDelivery(tipo);  // delivery = 1
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
        progress = new ProgressDialog(this);
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
                    uri = new URI("http", urlWeb, "/api/ApiEntrega/json?","destino=" + destinations,null);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                final String url = uri.toASCIIString();
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

}
