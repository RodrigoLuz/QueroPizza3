package com.pap.queropizza3.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.ViaCEP;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Intent it = new Intent(this, EstabelecimentoActivity.class);
        startActivity(it);
        finish();
    }

    private void salvarCliente(){
        TCliente c = new TCliente();
        c.setIdCliente(1);
        c.setNome(edtNome.getText().toString());
        c.setCep(edtCep.getText().toString());
        c.setEndereco(edtEndereco.getText().toString());
        c.setCidade(edtCidade.getText().toString());
        c.setUf(edtUf.getText().toString());
        c.setEmail(edtEmail.getText().toString());
        c.setTelefone(edtTelefone.getText().toString());

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

    public void btnBuscarClick(View view) {

//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
            // limpa
            this.edtCidade.setText("");
            this.edtEndereco.setText("");
            this.edtUf.setText("");

            // cep
            String cep = this.edtCep.getText().toString();

            // verifica se o CEP é válido
            Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
            Matcher matcher = pattern.matcher(cep);

            if (matcher.find()) {
                new DownloadCEPTask().execute(cep);
            } else {
                //JOptionPane.showMessageDialog(null, "Favor informar um CEP válido!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                new AlertDialog.Builder(this)
                        .setTitle("Aviso!")
                        .setMessage("Favor informar um CEP válido!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // nada
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
 /*           }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sem Internet!")
                    .setMessage("Não tem nenhuma conexão de rede disponível!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // nada
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
 */       }


    }


    private class DownloadCEPTask extends AsyncTask<String, Void, ViaCEP> {
        @Override
        protected ViaCEP doInBackground(String ... ceps) {
            ViaCEP vCep = null;

            try {
                vCep = new ViaCEP(ceps[0]);
            } finally {
                return vCep;
            }
        }

        @Override
        protected void onPostExecute(ViaCEP result) {
            if (result != null) {
                //txtBairro.setText(result.getBairro());
                //txtComplemento.setText(result.getComplemento());
                edtCidade.setText(result.getLocalidade());
                edtEndereco.setText(result.getLogradouro());
                edtUf.setText(result.getUf());
            }
        }
    }
}
