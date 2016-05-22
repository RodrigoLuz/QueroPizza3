package com.pap.queropizza3.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 19/05/2016.
 */
public class AppSQLDao {

    private AppSQLHelper helper;
    public AppSQLDao(Context ctx){
        helper = new AppSQLHelper(ctx);
    }

    public int inserirCardapioGrupo(TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_grupo_cod_grupo, grupo.getCodGrupo());
        cv.put(AppSQLHelper.f_grupo_nome, grupo.getNome());

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);
        db.close();
        return id;
    }

    public int inserirCardapioSubGrupo(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_sub_grupo_cod_sub_grupo, subgrupo.getCodSubGrupo());
        cv.put(AppSQLHelper.f_sub_grupo_nome, subgrupo.getNome());
        cv.put(AppSQLHelper.f_sub_grupo_grupo, subgrupo.getGrupo().getCodGrupo());

        int id = (int) db.insert(AppSQLHelper.t_sub_grupo, null, cv);
        db.close();
        return id;
    }


    public int inserirCardapioItem(TCardapioItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_item_nome, item.getNome());
        cv.put(AppSQLHelper.f_item_descricao, item.getDescricao());
        cv.put(AppSQLHelper.f_item_valor, item.getValor());
        cv.put(AppSQLHelper.f_item_sub_grupo, item.getSubgrupo().getCodSubGrupo());

        int id = (int) db.insert(AppSQLHelper.t_item, null, cv);
        db.close();
        return id;
    }

    public List<TCardapioItem> listaItem(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_item;
        String[] argumentos = null;
        if (subgrupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_item_sub_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(subgrupo.getCodSubGrupo()) };
        }
        sql += " ORDER BY "+ AppSQLHelper.f_item_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioItem> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cod_item = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_cod_item));
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
            item.setCodCardapioItem(cod_item);
            item.setNome(nome);
            item.setDescricao(descricao);
            item.setValor(valor);

            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public List<TCardapioSubGrupo> listaSubGrupo (TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_sub_grupo;
        String[] argumentos = null;
        if (grupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_sub_grupo_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(grupo.getCodGrupo()) };
        }
        sql += " ORDER BY "+ AppSQLHelper.f_sub_grupo_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioSubGrupo> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cod_sub_grupo = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_sub_grupo_cod_sub_grupo));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_sub_grupo_nome));

            TCardapioSubGrupo item = new TCardapioSubGrupo();
            item.setGrupo(grupo);
            item.setCodSubGrupo(cod_sub_grupo);
            item.setNome(nome);
            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public int inserirPedido(TPedido p) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_nome, p.getNome());
        cv.put(AppSQLHelper.f_ped_endereco, p.getEndereco());
        cv.put(AppSQLHelper.f_ped_numero, p.getNumero());
        cv.put(AppSQLHelper.f_ped_complemento, p.getComplemento());
        cv.put(AppSQLHelper.f_ped_bairro, p.getBairro());
        cv.put(AppSQLHelper.f_ped_cidade, p.getCidade());;
        cv.put(AppSQLHelper.f_ped_uf, p.getUf());;
        cv.put(AppSQLHelper.f_ped_email, p.getEmail());;
        cv.put(AppSQLHelper.f_ped_telefone, p.getTelefone());;
        cv.put(AppSQLHelper.f_ped_delivery, p.getDelivery());
        cv.put(AppSQLHelper.f_ped_taxa, p.getTaxa());
        cv.put(AppSQLHelper.f_ped_datahora, p.getDatahora());

        int id = (int) db.insert(AppSQLHelper.t_pedido, null, cv);
        p.setId_pedido(id);
        db.close();
        return id;
    }

    public int inserirCliente(TCliente c) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_cli_nome, c.getNome());
        cv.put(AppSQLHelper.f_cli_endereco, c.getEndereco());
        cv.put(AppSQLHelper.f_cli_numero, c.getNumero());
        cv.put(AppSQLHelper.f_cli_complemento, c.getComplemento());
        cv.put(AppSQLHelper.f_cli_bairro, c.getBairro());
        cv.put(AppSQLHelper.f_cli_cidade, c.getCidade());;
        cv.put(AppSQLHelper.f_cli_uf, c.getUf());;
        cv.put(AppSQLHelper.f_cli_email, c.getEmail());;
        cv.put(AppSQLHelper.f_cli_telefone, c.getTelefone());;

        int id = (int) db.insert(AppSQLHelper.t_cliente, null, cv);
        c.setId_cliente(id);
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


}
