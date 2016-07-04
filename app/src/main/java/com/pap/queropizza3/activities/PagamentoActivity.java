package com.pap.queropizza3.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.utils.EnviarPedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PagamentoActivity extends AppCompatActivity implements TrocoDialog.TrocoDialogListener {

    private static final String PAG_SEGURO_PACKAGE_NAME = "br.com.uol.ps";
    private static final String PAG_SEGURO_CLASS_NAME = "br.com.uol.ps.app.MainActivity";
    private static final String FLAG_APP_PAYMENT_VALUE = "FLAG_APP_PAYMENT_VALUE";
    private static final int REQUEST_CODE = 3016; // Pode ser o número de sua escolha
    CustomList adapter;

    ListView list;
    List<String> entrega = new ArrayList<String>();// {"Delivery", "Balcão"};
    List<Integer> imageId = new ArrayList<>(); //  {R.drawable.ic_action_mais, R.drawable.ic_action_menos};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        entrega.add("Dinheiro");
        imageId.add(R.drawable.pizza_icon);

        entrega.add("Mastercard - Crédito");
        imageId.add(R.drawable.pizza_icon);

        entrega.add("Mastercard - Débito");
        imageId.add(R.drawable.pizza_icon);

        entrega.add("Visa - Crédito");
        imageId.add(R.drawable.pizza_icon);

        entrega.add("Visa - Débito");
        imageId.add(R.drawable.pizza_icon);

        entrega.add("Pag Seguro");
        imageId.add(R.drawable.pizza_icon);


        adapter = new CustomList(this, entrega, imageId);
        list=(ListView)findViewById(R.id.lstvEntrega);
        list.setAdapter(adapter);
    }

    private void enviarPedido(int fp, double troco) {
        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1); // se retornar -1 tem algo errado
        TPedido p;
        p = dbDao.buscarPedido(id_pedido);
        p.setTroco_para(troco);
        p.setF_pagamento(fp);

        EnviarPedido e = new EnviarPedido();
        e.envia(p, getBaseContext());
    }

    private boolean checkIfAppIsInstalled() {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(PAG_SEGURO_PACKAGE_NAME,
                    PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }


    private boolean pagamentoPagSeguro() {
        boolean result = false;
        if (checkIfAppIsInstalled()) {
            BigDecimal paymentValue = new BigDecimal("22.00");
            Intent it = new Intent(Intent.ACTION_MAIN);
            it.setClassName(PAG_SEGURO_PACKAGE_NAME, PAG_SEGURO_CLASS_NAME); // Intent que será chamada.
            it.putExtra(FLAG_APP_PAYMENT_VALUE, paymentValue); // Valor da venda.
            try {
                startActivityForResult(it, REQUEST_CODE); // Chama o aplicativo do PagSeguro.
                result = true;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Erro ao abrir PagSeguro", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Aplicativo PagSeguro não instalado", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final List<String> entrega;
        private final List<Integer> imageId;
        public CustomList(Activity context, List<String> entrega, List<Integer> imageId) {
            super(context, R.layout.layout_item_entrega, entrega);
            this.context = context;
            this.entrega = entrega;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.layout_item_pagamento, null, true);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) { // dinheiro
                        showTrocoDialog();
                    } else
                    if (position == 5) // pag seguro
                    {
                        if (pagamentoPagSeguro()) {
                            enviarPedido(position, 0.00);
                            Intent it = new Intent(PagamentoActivity.this, MainActivity.class);
                            startActivity(it);
                        };
                    } else  // cartões
                    {
                        enviarPedido(position, 0.00);
                        Intent it = new Intent(PagamentoActivity.this, MainActivity.class);
                        startActivity(it);
                    }
                }
            });

            TextView txtvNome = (TextView) rowView.findViewById(R.id.txtvNome);
            ImageView imgView = (ImageView) rowView.findViewById(R.id.imgView);

            txtvNome.setText(entrega.get(position));
            imgView.setImageResource(imageId.get(position));
            return rowView;
        }
    }

    private void showTrocoDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TrocoDialog trocoDialog = new TrocoDialog();
        trocoDialog.show(fm, "fragment_troco");
    }

    @Override
    public void onFinishTrocoDialog(Double inputValor) {
//        Toast.makeText(this, "Hi, " + inputValor, Toast.LENGTH_SHORT).show();
        enviarPedido(0, inputValor);
        Intent it = new Intent(PagamentoActivity.this, MainActivity.class);
        startActivity(it);
    }

}
