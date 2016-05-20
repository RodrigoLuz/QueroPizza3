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
        cv.put(AppSQLHelper.t_grupo_cod_grupo, grupo.getCodGrupo());
        cv.put(AppSQLHelper.t_grupo_nome, grupo.getNome());

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);
        db.close();
        return id;
    }

    public int inserirCardapioSubGrupo(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.t_sub_grupo_nome, subgrupo.getNome());
        cv.put(AppSQLHelper.t_sub_grupo_grupo, subgrupo.getGrupo().getCodGrupo());

        int id = (int) db.insert(AppSQLHelper.t_sub_grupo, null, cv);
        db.close();
        return id;
    }


    public int inserirCardapioItem(TCardapioItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.t_item_nome, item.getNome());
        cv.put(AppSQLHelper.t_item_descricao, item.getDescricao());
        cv.put(AppSQLHelper.t_item_valor, item.getValor());
        cv.put(AppSQLHelper.t_item_sub_grupo, item.getSubgrupo().getCodSubGrupo());

        int id = (int) db.insert(AppSQLHelper.t_item, null, cv);
        db.close();
        return id;
    }

    public List<TCardapioItem> listaSabores (String filtro) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_item;
        String[] argumentos = null;
        if (filtro != null) {
            sql += " WHERE "+ AppSQLHelper.t_item_sub_grupo +" LIKE ?";
            argumentos = new String[]{ filtro };
        }
        sql += " ORDER BY "+ AppSQLHelper.t_item_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioItem> itens = new ArrayList<TCardapioItem>();
        while (cursor.moveToNext()) {
            int cod_item = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.t_item_cod_item));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.t_item_nome));
            String descricao = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.t_item_descricao));
            double valor = cursor.getFloat(
                    cursor.getColumnIndex(
                            AppSQLHelper.t_item_valor));

            TCardapioItem item = new TCardapioItem();
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


}
