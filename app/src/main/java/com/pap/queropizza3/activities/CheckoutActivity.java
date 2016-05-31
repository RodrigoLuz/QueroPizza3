package com.pap.queropizza3.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TEstabelecimento;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.models.TPedidoItem;
import com.pap.queropizza3.utils.EnviarPedido;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    List<TPedidoItem> itens = new ArrayList<TPedidoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        itens = dbDao.listaTodosPedidoItem();

        MyAdapter adapter = new MyAdapter(this, itens);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstvCheckout);
        listView.setAdapter(adapter);

    }

    public void btnFinalizarChkClick(View view) {
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo
        TPedido p;
        p = dbDao.buscarPedido(id_pedido);

        EnviarPedido e = new EnviarPedido();
        e.envia(p, getBaseContext());
    }

    public void btnAdicionarItensClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }

    public class MyAdapter extends ArrayAdapter<TPedidoItem> {
        public MyAdapter(Context context, List<TPedidoItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View vi = convertView;

            if (vi == null) {
                holder = new ViewHolder();
                TPedidoItem p = getItem(position);
                LayoutInflater inflater=getLayoutInflater();
                vi = inflater.inflate(R.layout.layout_item_checkout, parent, false);

                holder.txtvChkItem = (TextView)vi.findViewById(R.id.txtvChkItem);
                holder.txtvChkQuantidade = (TextView)vi.findViewById(R.id.txtvChkQuantidade);
                holder.txtvChkValor = (TextView)vi.findViewById(R.id.txtvChkValor);
                vi.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)vi.getTag();
            }

            TPedidoItem p = getItem(position);
            holder.txtvChkItem.setText(p.getSubitens().get(0).getCardapio_item().getNome());
            holder.txtvChkQuantidade.setText(String.valueOf(p.getQuantidade()));
            holder.txtvChkValor.setText(String.format( "%.2f", p.getValor()));

            return vi;
        }
    }

    private static class ViewHolder{
        TextView txtvChkItem, txtvChkQuantidade, txtvChkValor;
    }

}
