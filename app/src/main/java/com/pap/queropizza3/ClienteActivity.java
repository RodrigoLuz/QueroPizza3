package com.pap.queropizza3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ClienteActivity extends AppCompatActivity {

    Button btnSalvarCliente;
    EditText edtNome, edtCep, edtEndereco, edtCidade, edtUf, edtEmail, edtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        btnSalvarCliente = (Button)findViewById(R.id.btnSalvarCliente);
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtCep = (EditText)findViewById(R.id.edtCep);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtCidade = (EditText)findViewById(R.id.edtCidade);
        edtUf = (EditText)findViewById(R.id.edtUf);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
    }


    public void btnSalvarClienteClick(View v){
        salvarCliente();
    }

    private void salvarCliente(){
        TCliente c = new TCliente();
        c.idCliente = 1;
        c.nome = edtNome.getText().toString();
        c.cep = edtCep.getText().toString();
        c.endereco = edtEndereco.getText().toString();
        c.cidade = edtCidade.getText().toString();
        c.uf = edtUf.getText().toString();
        c.email = edtEmail.getText().toString();
        c.telefone = edtTelefone.getText().toString();

        String filename = "user.dat";
        FileOutputStream fileOut = null;

        try {
            fileOut = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(c);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
