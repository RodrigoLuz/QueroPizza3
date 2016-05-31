package com.pap.queropizza3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.models.TPedidoItem;
import com.pap.queropizza3.models.TPedidoDetalhe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 19/05/2016.
 */
public class AppSQLDao {

    private AppSQLHelper helper;
    private static final String TAG_INSERT_E = "Erro de insert: ";
    public AppSQLDao(Context ctx){
        helper = new AppSQLHelper(ctx);
    }

    public int inserirCardapioGrupo(TCardapioGrupo obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_grupo_cod_grupo, obj.getId_grupo());
        cv.put(AppSQLHelper.f_grupo_nome, obj.getNome());

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_grupo(id);
        db.close();
        return id;
    }

    public int inserirCardapioSubGrupo(TCardapioSubGrupo obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_sub_grupo_cod_sub_grupo, obj.getId_subgrupo());
        cv.put(AppSQLHelper.f_sub_grupo_nome, obj.getNome());
        cv.put(AppSQLHelper.f_sub_grupo_grupo, obj.getGrupo().getId_grupo());

        int id = (int) db.insert(AppSQLHelper.t_sub_grupo, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_subgrupo(id);
        db.close();
        return id;
    }

    public int inserirCardapioItem(TCardapioItem obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_item_cod_item, obj.getCod_item());
        cv.put(AppSQLHelper.f_item_nome, obj.getNome());
        cv.put(AppSQLHelper.f_item_descricao, obj.getDescricao());
        cv.put(AppSQLHelper.f_item_valor, obj.getValor());
        cv.put(AppSQLHelper.f_item_sub_grupo, obj.getSubgrupo().getId_subgrupo());

        int id = (int) db.insert(AppSQLHelper.t_item, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_item(id);
        db.close();
        return id;
    }

    public List<TCardapioItem> listaItem(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_item;
        String[] argumentos = null;
        if (subgrupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_item_sub_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(subgrupo.getId_subgrupo()) };
        }
        sql += " ORDER BY "+ AppSQLHelper.f_item_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioItem> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cod_item = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_id));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_nome));
            String descricao = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_descricao));
            double valor = cursor.getFloat(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_valor));

            TCardapioItem item = new TCardapioItem();
            item.setSubgrupo(subgrupo);
            item.setId_item(cod_item);
            item.setNome(nome);
            item.setDescricao(descricao);
            item.setValor(valor);

            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public TCardapioItem buscarCardapioItem(int id_item) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_item;
        String[] argumentos = null;
        sql += " WHERE "+ AppSQLHelper.f_item_id +" = ?";
        argumentos = new String[]{ String.valueOf(id_item) };
        Cursor c = db.rawQuery(sql, argumentos);
        if(c!=null){
            c.moveToFirst();
        }

//        int cod_item = c.getInt(c.getColumnIndex(AppSQLHelper.f_item_cod_item));
        String nome = c.getString(c.getColumnIndex(AppSQLHelper.f_item_nome));
        String descricao = c.getString(c.getColumnIndex(AppSQLHelper.f_item_descricao));
        double valor = c.getFloat(c.getColumnIndex(AppSQLHelper.f_item_valor));

        TCardapioItem item = new TCardapioItem();
        item.setId_item(id_item);
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setValor(valor);
        c.close();
        db.close();

        return item;
    }

    public List<TCardapioSubGrupo> listaSubGrupo (TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_sub_grupo;
        String[] argumentos = null;
        if (grupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_sub_grupo_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(grupo.getId_grupo()) };
        }

        sql += " ORDER BY "+ AppSQLHelper.f_sub_grupo_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioSubGrupo> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id_sub_grupo = cursor.getInt(cursor.getColumnIndex(AppSQLHelper.f_sub_grupo_id));
            int cod_sub_grupo = cursor.getInt(cursor.getColumnIndex(AppSQLHelper.f_sub_grupo_cod_sub_grupo));
            String nome = cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_sub_grupo_nome));

            TCardapioSubGrupo item = new TCardapioSubGrupo();
            item.setId_subgrupo(id_sub_grupo);
            item.setCod_subgrupo(cod_sub_grupo);
            item.setNome(nome);
            item.setGrupo(grupo);
            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public int inserirPedido(TPedido obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_nome, obj.getCliente().getNome());
        cv.put(AppSQLHelper.f_ped_endereco, obj.getCliente().getEndereco());
        cv.put(AppSQLHelper.f_ped_numero, obj.getCliente().getNumero());
        cv.put(AppSQLHelper.f_ped_complemento, obj.getCliente().getComplemento());
        cv.put(AppSQLHelper.f_ped_bairro, obj.getCliente().getBairro());
        cv.put(AppSQLHelper.f_ped_cidade, obj.getCliente().getCidade());;
        cv.put(AppSQLHelper.f_ped_uf, obj.getCliente().getUf());;
        cv.put(AppSQLHelper.f_ped_email, obj.getCliente().getEmail());;
        cv.put(AppSQLHelper.f_ped_telefone, obj.getCliente().getTelefone());;
        cv.put(AppSQLHelper.f_ped_delivery, obj.isDelivery());
        cv.put(AppSQLHelper.f_ped_taxa, obj.getTaxa());
        cv.put(AppSQLHelper.f_ped_datahora, obj.getDatahora());

        int id = (int) db.insert(AppSQLHelper.t_pedido, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_pedido(id);
        db.close();
        return id;
    }

    public TPedido buscarPedido(int id_pedido) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_pedido;
        String[] argumentos = null;
        sql += " WHERE "+ AppSQLHelper.f_ped_id +" = ?";
        argumentos = new String[]{ String.valueOf(id_pedido) };
        Cursor c = db.rawQuery(sql, argumentos);
        if(c!=null){
            c.moveToFirst();
        }
        TPedido p = new TPedido();
        p.setId_pedido(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_id)));
        p.setDelivery(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_delivery)));
        p.setTaxa(c.getDouble(c.getColumnIndex(AppSQLHelper.f_ped_taxa)));
        p.setDatahora(c.getString(c.getColumnIndex(AppSQLHelper.f_ped_delivery)));

        TCliente cli = new TCliente();
//        cli.setId_cliente(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_delivery)));
        cli.setNome(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_nome)));
//        cli.setSenha(c.getString(c.getColumnIndex(AppSQLHelper.f_ped_delivery)));
        cli.setCep(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_cep)));
        cli.setEndereco(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_endereco)));
        cli.setNumero(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_numero)));
        cli.setComplemento(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_complemento)));
        cli.setBairro(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_bairro)));
        cli.setCidade(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_cidade)));
        cli.setUf(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_uf)));
        cli.setEmail(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_email)));
        cli.setTelefone(c.getString(c.getColumnIndex(AppSQLHelper.f_cli_telefone)));

        p.setCliente(cli);
        p.setItens(listaPedidoItem(p));
        return p;
    }

    public List<TPedidoItem> listaPedidoItem (TPedido pedido) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_pedido_item;
        String[] argumentos = null;
        sql += " WHERE "+ AppSQLHelper.f_ped_item_pedido_id +" = ?";
        argumentos = new String[]{ String.valueOf(pedido.getId_pedido()) };
        sql += " ORDER BY "+ AppSQLHelper.f_ped_item_id;
        Cursor c = db.rawQuery(sql, argumentos);

        List<TPedidoItem> itens = new ArrayList<>();
        while (c.moveToNext()) {
            TPedidoItem item = new TPedidoItem();
            item.setId_item(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_id)));
            item.setId_pedido(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_pedido_id)));
            item.setQuantidade(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_quantidade)));
            item.setValor(c.getDouble(c.getColumnIndex(AppSQLHelper.f_ped_item_valor)));
            item.setTamanho(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_tamanho)));
            item.setObservacao(c.getString(c.getColumnIndex(AppSQLHelper.f_ped_item_obs)));
            item.setSubitens(listaPedidoDetalhe(item));
            itens.add(item);
        }
        c.close();
        db.close();
        return itens;
    }

    public List<TPedidoItem> listaTodosPedidoItem () {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_pedido_item;
        Cursor c = db.rawQuery(sql, null);

        List<TPedidoItem> itens = new ArrayList<>();
        while (c.moveToNext()) {
            TPedidoItem item = new TPedidoItem();
            item.setId_item(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_id)));
            item.setId_pedido(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_pedido_id)));
            item.setQuantidade(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_quantidade)));
            item.setValor(c.getDouble(c.getColumnIndex(AppSQLHelper.f_ped_item_valor)));
            item.setTamanho(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_item_tamanho)));
            item.setObservacao(c.getString(c.getColumnIndex(AppSQLHelper.f_ped_item_obs)));
            item.setSubitens(listaPedidoDetalhe(item));
            itens.add(item);
        }
        c.close();
        db.close();
        return itens;
    }


    public List<TPedidoDetalhe> listaPedidoDetalhe (TPedidoItem pedidoitem) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_pedido_detalhe;
        String[] argumentos = null;
        sql += " WHERE "+ AppSQLHelper.f_ped_detalhe_ped_itens_id +" = ?";
        argumentos = new String[]{ String.valueOf(pedidoitem.getId_item()) };
        sql += " ORDER BY "+ AppSQLHelper.f_ped_detalhe_id;
        Cursor c = db.rawQuery(sql, argumentos);

        List<TPedidoDetalhe> itens = new ArrayList<>();
        while (c.moveToNext()) {
            TPedidoDetalhe item = new TPedidoDetalhe();
            item.setId_detalhe(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_detalhe_id)));
            item.setId_item(c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_detalhe_ped_itens_id)));
            int id_item = c.getInt(c.getColumnIndex(AppSQLHelper.f_ped_detalhe_cardapio_id));
            item.setCardapio_item(buscarCardapioItem(id_item));
            itens.add(item);
        }
        c.close();
        db.close();
        return itens;
    }

    public int inserirCliente(TCliente obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_cli_nome, obj.getNome());
        cv.put(AppSQLHelper.f_cli_endereco, obj.getEndereco());
        cv.put(AppSQLHelper.f_cli_numero, obj.getNumero());
        cv.put(AppSQLHelper.f_cli_complemento, obj.getComplemento());
        cv.put(AppSQLHelper.f_cli_bairro, obj.getBairro());
        cv.put(AppSQLHelper.f_cli_cidade, obj.getCidade());;
        cv.put(AppSQLHelper.f_cli_uf, obj.getUf());;
        cv.put(AppSQLHelper.f_cli_email, obj.getEmail());;
        cv.put(AppSQLHelper.f_cli_telefone, obj.getTelefone());;

        int id = (int) db.insert(AppSQLHelper.t_cliente, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_cliente(id);
        db.close();
        return id;
    }

    public List<TCliente> listaCliente () {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_cliente;
        Cursor cursor = db.query(AppSQLHelper.t_cliente, null, null, null, null, null, null);
        List<TCliente> itens = new ArrayList<>();

        while (cursor.moveToNext()) {
            TCliente item = new TCliente();
            item.setId_cliente(cursor.getInt(cursor.getColumnIndex(AppSQLHelper.f_cli_id)));
            item.setNome(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_nome)));
            item.setCep(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_cep)));
            item.setEndereco(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_endereco)));
            item.setNumero(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_numero)));
            item.setComplemento(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_complemento)));
            item.setBairro(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_bairro)));
            item.setCidade(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_cidade)));
            item.setUf(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_uf)));
            item.setEmail(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_email)));
            item.setTelefone(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_telefone)));
            itens.add(item);
        }
        cursor.close();
        db.close();
        return itens;
    }

    public List<TItemTela> listaItensPorGrupo(int id_grupo){
        List<TItemTela> itenstela = new ArrayList<TItemTela>();

        TCardapioGrupo grupo = new TCardapioGrupo(); // cria um obj grupo com o código enviado para poder passar para lista
        grupo.setId_grupo(id_grupo); // grupo de pizzas, código está fixo, verificar

        List<TCardapioSubGrupo> subgrupos = listaSubGrupo(grupo); // percorre subgrupos do grupo informado e retorna or itens
        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TCardapioItem> itens = listaItem(subgrupos.get(i));
            for(int j = 0 ; j < itens.size(); j++){
                TItemTela linha = new TItemTela();
                linha.setCardapio_item(itens.get(j));
                itenstela.add(linha);
            }
        }
        return itenstela;
    }

    public List<TItemTela> listaItensPorSubGrupo(int id_subgrupo){
        List<TItemTela> itenstela = new ArrayList<TItemTela>();

        TCardapioSubGrupo subgrupo = new TCardapioSubGrupo(); // cria um obj subgrupo com o código enviado para poder passar para lista
        subgrupo.setId_subgrupo(id_subgrupo);

        List<TCardapioItem> itens = listaItem(subgrupo);
        for(int j = 0 ; j < itens.size(); j++){
            TItemTela linha = new TItemTela();
            linha.setCardapio_item(itens.get(j));
            itenstela.add(linha);
        }
        return itenstela;
    }

    public int inserirPedidoItem(TPedidoItem obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_item_pedido_id, obj.getId_pedido());
        cv.put(AppSQLHelper.f_ped_item_quantidade, obj.getQuantidade());
        cv.put(AppSQLHelper.f_ped_item_valor, obj.getValor());
        cv.put(AppSQLHelper.f_ped_item_tamanho, obj.getTamanho());
        cv.put(AppSQLHelper.f_ped_item_obs, obj.getObservacao());

        int id = (int) db.insert(AppSQLHelper.t_pedido_item, null, cv);
        if (id == -1) {
            Log.e(TAG_INSERT_E, obj.toString());
        }
        obj.setId_item(id);
        db.close();
        return id;
    }

    public int inserirPedidoSubItem(TPedidoDetalhe obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_detalhe_ped_itens_id, obj.getId_item());
        cv.put(AppSQLHelper.f_ped_detalhe_cardapio_id, obj.getCardapio_item().getId_item());
        cv.put(AppSQLHelper.f_ped_detalhe_valor, obj.getCardapio_item().getValor());

        int id = (int) db.insert(AppSQLHelper.t_pedido_detalhe, null, cv);
        if (id == -1) {
            Log.v(TAG_INSERT_E, obj.toString());
        }
        obj.setId_detalhe(id);
        db.close();
        return id;
    }

    public void apagarCardapio(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AppSQLHelper.t_grupo, null, null);
        db.close();
    }

    public void apagarPedido(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AppSQLHelper.t_pedido, null, null);
        db.close();
    }

    public TCardapioGrupo buscarGrupo(int cod_grupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_grupo;
        String[] argumentos = null;
        sql += " WHERE "+ AppSQLHelper.f_grupo_cod_grupo +" = ?";
        argumentos = new String[]{ String.valueOf(cod_grupo) };
        Cursor c = db.rawQuery(sql, argumentos);
        if(c!=null){
            c.moveToFirst();
        }

        int id_grupo = c.getInt(c.getColumnIndex(AppSQLHelper.f_grupo_id));
        String nome = c.getString(c.getColumnIndex(AppSQLHelper.f_grupo_nome));

        TCardapioGrupo grupo = new TCardapioGrupo();
        grupo.setId_grupo(id_grupo);
        grupo.setCod_grupo(cod_grupo);
        grupo.setNome(nome);
        c.close();
        db.close();

        return grupo;
    }

}
