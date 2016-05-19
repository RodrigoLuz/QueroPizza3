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
    public static final String t_grupo_id = "id_grupo integer primary key autoincrement, ";
    public static final String t_grupo_cod_grupo = "cod_grupo integer, ";
    public static final String t_grupo_nome = "nome text";

    public static final String t_sub_grupo = "cardapio_sub_grupo";
    public static final String t_sub_grupo_id = "id_sub_grupo integer primary key autoincrement, ";
    public static final String t_sub_grupo_cod_sub_grupo = "cod_sub_grupo integer, ";
    public static final String t_sub_grupo_nome = "nome text, ";
    public static final String t_sub_grupo_grupo = "id_grupo integer";

    public static final String t_item = "cardapio_item";
    public static final String t_item_id = "id_item integer primary key autoincrement, ";
    public static final String t_item_cod_item = "cod_item integer, ";
    public static final String t_item_nome = "nome text, ";
    public static final String t_item_descricao = "descricao text, ";
    public static final String t_item_valor = "valor real, ";
    public static final String t_item_sub_grupo = "id_sub_grupo integer";

    public AppSQLHelper(Context context) {
        super(context, nome_banco, null, versao_banco);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + t_grupo + "(" +
                        t_grupo_id + t_grupo_cod_grupo + t_grupo_nome + ")"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_sub_grupo + "(" +
                        t_sub_grupo_id + t_sub_grupo_cod_sub_grupo + t_sub_grupo_nome + t_sub_grupo_grupo + ")"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_item + "(" +
                        t_item_id + t_item_cod_item + t_item_nome + t_item_descricao + t_item_valor + t_item_sub_grupo + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}


//  public static final String coluna_ = "";