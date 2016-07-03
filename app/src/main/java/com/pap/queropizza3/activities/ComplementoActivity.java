package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoDetalhe;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.List;

public class ComplementoActivity extends AppCompatActivity {

    List<TItemTela> dados = new ArrayList<TItemTela>();
    int tamanho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complemento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravarLista(dados);
                Intent it = new Intent(ComplementoActivity.this, GrupoActivity.class);
                startActivity(it);
            }
        });

        if(getIntent().hasExtra("saboresSelecionados")) {
            Bundle bundleObject = getIntent().getExtras();
            dados = (ArrayList<TItemTela>) bundleObject.getSerializable("saboresSelecionados");
        }

        if(getIntent().hasExtra("tamanho")) {
            Bundle b = new Bundle();
            b = getIntent().getExtras();
            tamanho = b.getInt("tamanho");
        }
    }


    public void gravarLista(List<TItemTela> dados) {
        this.dados = dados;

        String desc = "Pizza ";
        double valor = 0;

        switch (tamanho) {
            case 1:
                desc += "gigante";
                break;
            case 2:
                desc += "grande";
                break;
            case 3:
                desc += "média";
                break;
            case 4:
                desc += "pequena";
                break;
        }

        //Restaura id_pedido gravado
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1);
        int id_item;

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());

        TPedidoItem item = new TPedidoItem();
        item.setId_pedido(id_pedido);
        item.setQuantidade(1);

        for(int j = 0 ; j < dados.size(); j++) {
            TCardapioItem cardapioItem = dados.get(j).getCardapio_item();
            if (cardapioItem.getValor() > valor){
                valor = cardapioItem.getValor();
            }
        }

        desc += String.format( " R$ %.2f", valor);
        item.setDescricao(desc);

        item.setValor(valor);  // verificar como gravar do maior valor dos itens, não poderá ser do cardápio
        item.setGrupo(1);  // grupo pizza é definido como 1
        item.setSubgrupo(0);  // para pizza não é necessário
        item.setTamanho(tamanho);
        id_item = dbDao.inserirPedidoItem(item);

        for(int j = 0 ; j < dados.size(); j++) {
            TPedidoDetalhe detalhe = new TPedidoDetalhe();
            TCardapioItem cardapioItem = dados.get(j).getCardapio_item();

            detalhe.setId_item(id_item);
            detalhe.setCardapio_item(cardapioItem);
            dbDao.inserirPedidoSubItem(detalhe);
        }
    }


}
