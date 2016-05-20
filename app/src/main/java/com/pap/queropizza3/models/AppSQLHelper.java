package com.pap.queropizza3.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo on 19/05/2016.
 * http://www.tutorialspoint.com/android/android_sqlite_database.htm
 * http://www.mobiltec.com.br/blog/index.php/android-persistencia-de-dados-usando-sqlite/
 */
public class AppSQLHelper extends SQLiteOpenHelper {

    private static final String nome_banco = "db_app";
    private static final int versao_banco = 1;

    public static final String t_grupo = "cardapio_grupo";
    public static final String t_grupo_id = "id_grupo";
    public static final String t_grupo_cod_grupo = "cod_grupo";
    public static final String t_grupo_nome = "nome";

    public static final String t_sub_grupo = "cardapio_sub_grupo";
    public static final String t_sub_grupo_id = "id_sub_grupo";
    public static final String t_sub_grupo_cod_sub_grupo = "cod_sub_grupo";
    public static final String t_sub_grupo_nome = "nome";
    public static final String t_sub_grupo_grupo = "id_grupo";

    public static final String t_item = "cardapio_item";
    public static final String t_item_id = "id_item";
    public static final String t_item_cod_item = "cod_item";
    public static final String t_item_nome = "nome";
    public static final String t_item_descricao = "descricao";
    public static final String t_item_valor = "valor";
    public static final String t_item_sub_grupo = "id_sub_grupo";

    public AppSQLHelper(Context context) {
        super(context, nome_banco, null, versao_banco);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + t_grupo + "(" +
                        t_grupo_id + " integer primary key autoincrement, " +
                        t_grupo_cod_grupo + " integer," +
                        t_grupo_nome + " text)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_sub_grupo + "(" +
                        t_sub_grupo_id + " integer primary key autoincrement, " +
                        t_sub_grupo_cod_sub_grupo + " integer, " +
                        t_sub_grupo_nome + " text, " +
                        t_sub_grupo_grupo + " integer)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_item + "(" +
                        t_item_id + " integer primary key autoincrement, " +
                        t_item_cod_item + " integer, " +
                        t_item_nome + " text, " +
                        t_item_descricao + " text, " +
                        t_item_valor + " real, " +
                        t_item_sub_grupo + " integer)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}


//  public static final String coluna_ = "";