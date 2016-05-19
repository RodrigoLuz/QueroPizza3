package com.pap.queropizza3.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rodrigo on 19/05/2016.
 */
public class AppSQLDao {

    private AppSQLHelper helper;

    public AppSQLDao(Context ctx){
        helper = new AppSQLHelper(ctx);
    }

    private int inserir(TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.t_grupo_nome, grupo.nome);

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);

        if (id != -1){
            grupo.setCodGrupo(id);
        }
        db.close();
        return id;
    }

    private int inserir(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.t_sub_grupo_nome, subgrupo.nome);
        cv.put(AppSQLHelper.t_sub_grupo_grupo, String.valueOf(subgrupo.grupo));

  //      continuar

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);

        if (id != -1){
            subgrupo. setCodSubGrupo(id);
        }
        db.close();
        return id;
    }

    private int inserir(TCardapioItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.t_grupo_nome, item.nome);

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);

        if (id != -1){
            item.setCodCardapioItem(id);
        }
        db.close();
        return id;
    }


}
