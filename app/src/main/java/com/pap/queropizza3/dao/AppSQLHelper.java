package com.pap.queropizza3.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo on 19/05/2016.
 * http://www.tutorialspoint.com/android/android_sqlite_database.htm
 * http://www.mobiltec.com.br/blog/index.php/android-persistencia-de-dados-usando-sqlite/
 * https://cmanios.wordpress.com/2012/05/17/extend-sqliteopenhelper-as-a-singleton-class-in-android/
 */
public class AppSQLHelper extends SQLiteOpenHelper {

    private static final String nome_banco = "db_app";
    private static final int versao_banco = 1;
    private final Context myContext;
    private static AppSQLHelper sInstance;


    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    private AppSQLHelper(Context context) {
        super(context, nome_banco, null, versao_banco);
        this.myContext = context;
    }

    public static synchronized AppSQLHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new AppSQLHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        AppCriaTabelas c = new AppCriaTabelas();
        c.criaTabelas(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void close() {
        super.close();
        if (sInstance.getWritableDatabase() != null) {
            sInstance.getWritableDatabase().close();
//            myWritableDb = null;
        }
    }

}
