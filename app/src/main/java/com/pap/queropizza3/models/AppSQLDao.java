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

}
