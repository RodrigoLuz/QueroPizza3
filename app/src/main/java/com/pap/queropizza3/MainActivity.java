package com.pap.queropizza3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnTesteClick(View v){
        if (fileExistance("user.dat")) {
            Intent it = new Intent(this, EstabelecimentoActivity.class);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(this, ClienteActivity.class);
            startActivity(it);
        }
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

}

// chave api: AIzaSyCmCqojmk_KOO4B6ffzHwSDFElMnqUCz0w
