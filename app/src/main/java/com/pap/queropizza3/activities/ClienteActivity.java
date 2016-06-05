package com.pap.queropizza3.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.utils.ViaCEP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteActivity extends AppCompatActivity {

    Button btnSalvarCliente;
    EditText edtNome, edtCep, edtEndereco, edtCidade, edtNumero, edtComplemento, edtBairro, edtUf, edtEmail, edtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        btnSalvarCliente = (Button)findViewById(R.id.btnSalvarCliente);
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtCep = (EditText)findViewById(R.id.edtCep);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtNumero = (EditText)findViewById(R.id.edtNumero);
        edtComplemento = (EditText)findViewById(R.id.edtComplemento);
        edtBairro = (EditText)findViewById(R.id.edtBairro);
        edtCidade = (EditText)findViewById(R.id.edtCidade);
        edtUf = (EditText)findViewById(R.id.edtUf);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);


        if(getIntent().hasExtra("cliente")){
            TCliente c = (TCliente)getIntent().getSerializableExtra("cliente");

            edtNome.setText(c.getNome());
            edtCep.setText(c.getCep());
            edtEndereco.setText(c.getEndereco());
            edtNumero.setText(c.getNumero());
            edtComplemento.setText(c.getComplemento());
            edtBairro.setText(c.getBairro());
            edtCidade.setText(c.getCidade());
            edtUf.setText(c.getUf());
            edtEmail.setText(c.getEmail());
            edtTelefone.setText(c.getTelefone());
        }

    }


    public void btnSalvarClienteClick(View v){
        salvarCliente();
        Intent it = new Intent(this, EstabelecimentoActivity.class);
        startActivity(it);
        finish();
    }

    private void salvarCliente(){
        TCliente c = new TCliente();
        c.setNome(edtNome.getText().toString());
        c.setCep(edtCep.getText().toString());
        c.setEndereco(edtEndereco.getText().toString());
        c.setNumero(edtNumero.getText().toString());
        c.setComplemento(edtComplemento.getText().toString());
        c.setBairro(edtBairro.getText().toString());
        c.setCidade(edtCidade.getText().toString());
        String input = edtUf.getText().toString();
        input = input.toUpperCase(); //converts the string to uppercase
        c.setUf(input);
        c.setEmail(edtEmail.getText().toString());
        c.setTelefone(edtTelefone.getText().toString());

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        dbDao.inserirCliente(c);
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
            this.edtBairro.setText("");
            this.edtComplemento.setText("");
            this.edtNumero.setText("");

            // cep
            String cep = this.edtCep.getText().toString();

            // verifica se o CEP é válido
            Pattern pattern = Pattern.compile("^[0-9]{5}-?[0-9]{3}$");
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
                edtCidade.setText(result.getLocalidade());
                edtEndereco.setText(result.getLogradouro());
                edtUf.setText(result.getUf());
                edtBairro.setText(result.getBairro());
                edtComplemento.setText(result.getComplemento());
            }
        }
    }
}
