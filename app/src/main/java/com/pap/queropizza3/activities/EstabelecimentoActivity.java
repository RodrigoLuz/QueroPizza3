package com.pap.queropizza3.activities;

import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TEstabelecimento;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
// https://dzone.com/articles/android-listview-optimizations
// https://dzone.com/articles/optimizing-your-listview

public class EstabelecimentoActivity extends AppCompatActivity {

    String url;
    List<TEstabelecimento> estabelecimentos = new ArrayList<TEstabelecimento>();
    AppSQLDao dbDao;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    montaLista();
                    break;
                case 2:
                    Toast.makeText(EstabelecimentoActivity.this, "Erro de conexão", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbDao = new AppSQLDao(getApplicationContext());

        buscarEstabelecimentos();

        dbDao.limparCardapio();
        buscarCardapio();
    }

    private void montaLista() {
        setContentView(R.layout.activity_estabelecimento);

        // Create the adapter to convert the array to views
        MyAdapter adapter = new MyAdapter(this, estabelecimentos);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstvEstabelecimentos);
        listView.setAdapter(adapter);
    }

    public class MyAdapter extends ArrayAdapter<TEstabelecimento> {
        public MyAdapter(Context context, List<TEstabelecimento> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View vi = convertView;

            if (vi == null) {
                holder = new ViewHolder();
                TEstabelecimento e = getItem(position);
                LayoutInflater inflater=getLayoutInflater();
                vi = inflater.inflate(R.layout.layout_lstv_estabelecimento, parent, false);

                holder.txtvNome = (TextView)vi.findViewById(R.id.txtvNome);
                holder.txtvInfo1 = (TextView)vi.findViewById(R.id.txtvInfo1);
                holder.txtvInfo2 = (TextView)vi.findViewById(R.id.txtvInfo2);
                holder.imgBtnInfoEstabelecimento = (ImageButton)vi.findViewById(R.id.imgBtnInfoEstabelecimento);

                ListView l = (ListView) findViewById(R.id.lstvEstabelecimentos);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent it = new Intent(EstabelecimentoActivity.this, RetiradaActivity.class);
                        startActivity(it);
                    }
                });

                holder.imgBtnInfoEstabelecimento.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(EstabelecimentoActivity.this, EstabelecimentoDetalheActivity.class);
                        startActivity(it);
                    }
                });

                vi.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)vi.getTag();
            }

            TEstabelecimento e = getItem(position);
            holder.txtvNome.setText(e.getNome());
            holder.txtvInfo1.setText(e.getEndereco());
            holder.txtvInfo2.setText(e.getCidade());

            return vi;
        }
    }

    private static class ViewHolder{
        TextView txtvNome, txtvInfo1, txtvInfo2;
        ImageButton imgBtnInfoEstabelecimento;
    }

    public void buscarEstabelecimentos() {
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = AndroidHttpClient.newInstance("HttpAndroid");
                url = "http://www.digibyte.com.br/particular/json.txt";
                //url = "http://queropizzaweb.azurewebsites.net/api/apipizzarias";
                HttpGet get = new HttpGet(url);

                try {
                    HttpResponse response = client.execute(get);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        String dadosServidor = EntityUtils.toString(entity);

                        JSONObject objectRoot = new JSONObject(dadosServidor);
                    //    JSONArray arrayEstabelecimentos = objectRoot.getJSONArray("Pizzarias");
                        JSONArray arrayEstabelecimentos = objectRoot.getJSONArray("estabelecimentos");
                        for(int i = 0; i < arrayEstabelecimentos.length(); i++) {
                            TEstabelecimento e = new TEstabelecimento();
                            JSONObject object = arrayEstabelecimentos.getJSONObject(i);
                            e.setNome(object.getString("nome"));
                            e.setEndereco(object.getString("endereco"));
                            estabelecimentos.add(e);
                        }

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

    public void buscarCardapio() {
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = AndroidHttpClient.newInstance("HttpAndroid");
                url = "http://queropizzaweb.azurewebsites.net/api/ApiCardapios";
                HttpGet get = new HttpGet(url);

                try {
                    HttpResponse response = client.execute(get);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        String dadosServidor = EntityUtils.toString(entity);

                        JSONObject objectRoot = new JSONObject(dadosServidor);

                        JSONArray arrayCardapioGrupo = objectRoot.getJSONArray("Cardapio");
                        for(int i = 0; i < arrayCardapioGrupo.length(); i++) {
                            TCardapioGrupo cardapiogrupo = new TCardapioGrupo();
                            JSONObject objectgrupo = arrayCardapioGrupo.getJSONObject(i);
                            cardapiogrupo.setId_grupo(objectgrupo.getInt("ClasseCardapioID"));
                            cardapiogrupo.setNome(objectgrupo.getString("Nome")); // pizza, bebidas
                            dbDao.inserirCardapioGrupo(cardapiogrupo);

                            JSONArray arrayCardapioSubGrupo = objectgrupo.getJSONArray("SubClasseCardapio");
                            for(int j = 0; j < arrayCardapioSubGrupo.length(); j++) {
                                TCardapioSubGrupo cardapiosubgrupo = new TCardapioSubGrupo();
                                JSONObject objectsubgrupo = arrayCardapioSubGrupo.getJSONObject(j);
                                cardapiosubgrupo.setId_subgrupo(objectsubgrupo.getInt("SubClasseCardapioID"));
                                cardapiosubgrupo.setNome(objectsubgrupo.getString("Nome")); // pizza doce, pizza salgada, refrigerantes, vinho
                                cardapiosubgrupo.setGrupo(cardapiogrupo);
                                dbDao.inserirCardapioSubGrupo(cardapiosubgrupo);

                                JSONArray arrayCardapioItem = objectsubgrupo.getJSONArray("ItemCardapio");
                                for(int k = 0; k < arrayCardapioItem.length(); k++) {
                                    TCardapioItem cardapioitem = new TCardapioItem();
                                    JSONObject objectitem = arrayCardapioItem.getJSONObject(k);
                                    cardapioitem.setCod_item(objectitem.getInt("ItemCardapioID"));
                                    cardapioitem.setNome(objectitem.getString("Nome")); // calabresa, mussarela, guaraná
                                    cardapioitem.setValor(objectitem.getDouble("Preco"));
                                    cardapioitem.setDescricao(objectitem.getString("Insumos"));
                                    cardapioitem.setSubgrupo(cardapiosubgrupo);
                                    dbDao.inserirCardapioItem(cardapioitem);
                                }
                            }
                        }

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


