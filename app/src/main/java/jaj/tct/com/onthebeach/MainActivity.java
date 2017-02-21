package jaj.tct.com.onthebeach;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import jaj.tct.com.onthebeach.Auxiliares.NavigationItemSelected;
import jaj.tct.com.onthebeach.Auxiliares.ReplaceFragment;
import jaj.tct.com.onthebeach.Auxiliares.VerificaConexao;
import jaj.tct.com.onthebeach.fragments.PesquisaEstado;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    ReplaceFragment replaceFragmentOjb;

    private ConexaoReceiver mReceiver;
    public boolean SAIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        SAIR = false;

        //------ TOOLBAR -------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PesquisaEstado pesquisaEstado = new PesquisaEstado();
        ReplaceFragment replaceFragmentOjb= new ReplaceFragment(this);
        replaceFragmentOjb.replaceFragment(pesquisaEstado, "fragmentEstados");

        //------ TABS -------
        /*final AbasPagerAdapter pagerAdapter = new AbasPagerAdapter(this, getSupportFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //ReplaceFragment(pagerAdapter.getItem(0));
        replaceFragmentOjb= new ReplaceFragment(this);
        replaceFragmentOjb.replaceFragment(pagerAdapter.getItem(0), "fragmentEstados");
        //------ SETANDO LISTENER DA VIEWPAGER ------
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    replaceFragmentOjb.replaceFragment(pagerAdapter.getItem(position), "fragmentEstados");
                }else if(position==1){
                    replaceFragmentOjb.replaceFragment(pagerAdapter.getItem(position), "fragmentMap");
                }else{
                    replaceFragmentOjb.replaceFragment(pagerAdapter.getItem(position), "fragmentEstados");
                }

            }

            // Não está sendo utilizado
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });*/


        //------ NAVIGATION VIEW -------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //----------------- NAVIGATION DRAWER ------------------------
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount()>1) {
                getSupportFragmentManager().popBackStack();
                SAIR = false;
            }else{
                if(SAIR){
                    SAIR = false;
                    this.finish();
                }
                Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();
                SAIR = true;

            }

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        NavigationItemSelected auxiliar = new NavigationItemSelected(this);

        return auxiliar.onNavigationItemSelected(item);
    }

    //----------------- LINK SITE WEB ------------------------
    public void webSiteOTB(View v){
        Uri uri = Uri.parse("http://www.onthebeach.com.br/index/index.html");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new ConexaoReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mReceiver);
    }

    class ConexaoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            VerificaConexao v = new VerificaConexao(MainActivity.this);
            if(!v.existeConexao()){
                Toast.makeText(context, "Sem conexão com a internet, favor verificar!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
