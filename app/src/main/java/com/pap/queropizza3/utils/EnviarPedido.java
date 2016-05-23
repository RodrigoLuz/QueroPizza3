package com.pap.queropizza3.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.pap.queropizza3.models.TPedido;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rodrigo on 22/05/2016.
 * http://hmkcode.com/android-send-json-data-to-server/
 * http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient
 * https://trinitytuts.com/post-json-data-to-server-in-android/
 * http://www.portugal-a-programar.pt/topic/61761-resolvido-android-envio-json-e-recepcao/
 * http://www.jiahaoliuliu.com/2011/09/basic-guide-of-json-on-java-and-android.html
 * http://stackoverflow.com/questions/13272516/gson-include-class-name-when-serializing-java-pojo-json
 */
public class EnviarPedido {

    TPedido p = new TPedido();
    public void envia(TPedido pedido) {
        p = pedido;
        new HttpAsyncTask().execute("http://queropizzaweb.azurewebsites.net/api/ApiPedidos");
    }

        public class HttpAsyncTask extends AsyncTask <String, Void, String> {
            @Override
            public String doInBackground(String... urls) {
                return POST(urls[0], p);
            }

            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            }
        }

    public static String POST(String url, TPedido pedido){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("delivery", pedido.isDelivery());
            jsonObject.accumulate("taxa", pedido.getTaxa());
            jsonObject.accumulate("datahora", pedido.getDatahora());
            jsonObject.accumulate("nome", pedido.getCliente().getNome());
            jsonObject.accumulate("cep", pedido.getCliente().getCep());
            jsonObject.accumulate("endereco", pedido.getCliente().getEndereco());
            jsonObject.accumulate("numero", pedido.getCliente().getNumero());
            jsonObject.accumulate("complemento", pedido.getCliente().getComplemento());
            jsonObject.accumulate("bairro", pedido.getCliente().getBairro());
            jsonObject.accumulate("cidade", pedido.getCliente().getCidade());
            jsonObject.accumulate("uf", pedido.getCliente().getUf());
            jsonObject.accumulate("email", pedido.getCliente().getEmail());
            jsonObject.accumulate("telefone", pedido.getCliente().getTelefone());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}
