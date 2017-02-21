package jaj.tct.com.onthebeach.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import jaj.tct.com.onthebeach.Auxiliares.NavigationItemSelected;
import jaj.tct.com.onthebeach.MainActivity;
import jaj.tct.com.onthebeach.R;

public class LogadoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logado);

        //------ TOOLBAR -------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void deslogar(View view){
        /** Removendo os dados do token do SharedPreferences */
        SharedPreferences prefs = getSharedPreferences(LoginActivity.CHAVE_PREFERENCES_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("status","");
        editor.putString("token","");
        editor.commit();

        /** Iniciando a activity LoginActivity */
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    /** Método que chama a classe responsável por controlar os clicks do navigation view (drawer layout) */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationItemSelected auxiliar = new NavigationItemSelected(this);

        return auxiliar.onNavigationItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }
}
