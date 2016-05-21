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

    public static final String t_grupo = "cardapio_grupo"; // tabela
    public static final String f_grupo_id = "id_grupo";
    public static final String f_grupo_cod_grupo = "cod_grupo";
    public static final String f_grupo_nome = "nome";

    public static final String t_sub_grupo = "cardapio_sub_grupo"; // tabela
    public static final String f_sub_grupo_id = "id_sub_grupo";
    public static final String f_sub_grupo_cod_sub_grupo = "cod_sub_grupo";
    public static final String f_sub_grupo_nome = "nome";
    public static final String f_sub_grupo_grupo = "id_grupo";

    public static final String t_item = "cardapio_item"; // tabela
    public static final String f_item_id = "id_item";
    public static final String f_item_cod_item = "cod_item";
    public static final String f_item_nome = "nome";
    public static final String f_item_descricao = "descricao";
    public static final String f_item_valor = "valor";
    public static final String f_item_sub_grupo = "id_sub_grupo";

    public AppSQLHelper(Context context) {
        super(context, nome_banco, null, versao_banco);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + t_grupo + "(" +
                        f_grupo_id + " integer primary key autoincrement, " +
                        f_grupo_cod_grupo + " integer," +
                        f_grupo_nome + " text)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_sub_grupo + "(" +
                        f_sub_grupo_id + " integer primary key autoincrement, " +
                        f_sub_grupo_cod_sub_grupo + " integer, " +
                        f_sub_grupo_nome + " text, " +
                        f_sub_grupo_grupo + " integer)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_item + "(" +
                        f_item_id + " integer primary key autoincrement, " +
                        f_item_cod_item + " integer, " +
                        f_item_nome + " text, " +
                        f_item_descricao + " text, " +
                        f_item_valor + " real, " +
                        f_item_sub_grupo + " integer)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}


//  public static final String coluna_ = "";