package jaj.tct.com.onthebeach.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jaj.tct.com.onthebeach.Auxiliares.NavigationItemSelected;
import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Login;
import jaj.tct.com.onthebeach.models.Usuario;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jocsa on 20/10/16.
 */
public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private static final String URL_LOGIN = "http://www.eukip.com/aulas/UserAuth/Home/PostAuth";
    public static final String CHAVE_PREFERENCES_LOGIN = "login";

    private ProgressDialog dialog;

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private EditText etUsuario;
    private EditText etSenha;
    private CheckBox cbPermanecerLogado;
    private Login login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //------ TOOLBAR -------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.etUsuario = (EditText) findViewById(R.id.etLoginUsuario);
        this.etSenha = (EditText) findViewById(R.id.etLoginSenha);
        this.cbPermanecerLogado = (CheckBox) findViewById(R.id.checkBoxLogin);

        /** Verificando se existe token válido, ou seja, se existe usuário logado */
        SharedPreferences prefs = getSharedPreferences(CHAVE_PREFERENCES_LOGIN, MODE_PRIVATE);
        String status = prefs.getString("status", null);
        if("ok".equals(status)){
            Intent i = new Intent(this, LogadoActivity.class);
            startActivity(i);
            this.finish();
        }
    }

    /** Método que chama a classe responsável por controlar os clicks do navigation view (drawer layout) */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationItemSelected auxiliar = new NavigationItemSelected(this);

        return auxiliar.onNavigationItemSelected(item);
    }

    public void Logar(View view){
        if(etUsuario.getText().toString().trim().isEmpty() || etSenha.getText().toString().trim().isEmpty()){
            Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }else{
            dialog = ProgressDialog.show(this, "Login", "Conectando, por favor aguarde.", false, false);
            if(existeConexao()){
                new LoginTask().execute(this.etUsuario.getText().toString(), this.etSenha.getText().toString());
            }else{
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Sem conexão com a internet!\nFavor verificar sua conexão!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarToken(){
        SharedPreferences prefs = getSharedPreferences(CHAVE_PREFERENCES_LOGIN, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("status",login.getStatus());
        editor.putString("token",login.getToken());
        editor.commit();
    }


    /**------------ LOGIN ASYNCTASK ------------*/
    class LoginTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            OkHttpClient cliente = new OkHttpClient();

            Usuario usuario = new Usuario();
            usuario.setLogin(params[0]);
            usuario.setSenha(params[1]);

            Gson gson = new Gson();
            String json = gson.toJson(usuario);

            RequestBody body = new FormBody.Builder()
                    .add("json",json)
                    .build();

            Request request = new Request.Builder().url(URL_LOGIN).post(body).build();
            try {
                Response response = cliente.newCall(request).execute();
                String resposta = response.body().string();
                login = gson.fromJson(resposta, Login.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(login!=null){
                Log.i("verjson",login.getStatus());
                if (login.getStatus().equals("ok")){
                    if (cbPermanecerLogado.isChecked()){
                        guardarToken();
                    }
                    Intent i = new Intent(getContext(), LogadoActivity.class);
                    startActivity(i);
                    getContext().finish();
                }else{
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Usuario ou Senha são inválidos, tente novamente!", Toast.LENGTH_LONG).show();
                    etUsuario.getText().clear();
                    etSenha.getText().clear();
                }
            }else{
                dialog.dismiss();
                Toast.makeText(getContext(), "Ocorreu um erro durante o login, tente novamente!", Toast.LENGTH_SHORT).show();
                etUsuario.getText().clear();
                etSenha.getText().clear();
            }
        }

    }

    /**Método retorna a instância da activity atual para ser usada no TOAST do onPostExecute da classe LoginTask*/
    public Activity getContext(){
        return this;
    }

    public boolean existeConexao(){

        ConnectivityManager connectivity = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivity!=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if(info==null){
                return false;
            }else{
                int tipoDeConexao = info.getType();
                if (tipoDeConexao == ConnectivityManager.TYPE_WIFI ||
                        tipoDeConexao == ConnectivityManager.TYPE_MOBILE) {
                    return info.isConnected();
                } else {
                    return false;
                }
            }
        }else{
            return false;
        }
    }
}
