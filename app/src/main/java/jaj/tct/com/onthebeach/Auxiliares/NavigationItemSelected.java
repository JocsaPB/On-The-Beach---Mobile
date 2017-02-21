package jaj.tct.com.onthebeach.Auxiliares;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.activitys.LoginActivity;
import jaj.tct.com.onthebeach.fragments.CreditosFragment;
import jaj.tct.com.onthebeach.fragments.DesenvolvedoresFragment;
import jaj.tct.com.onthebeach.fragments.MapAbrangente;
import jaj.tct.com.onthebeach.fragments.UltimasLojasPesquisadasFragment;

/**
 * Created by jocsa on 20/10/16.
 */
public class NavigationItemSelected<T extends AppCompatActivity>{

    private T activity;

    public NavigationItemSelected(T activity){
        this.activity = activity;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.lojasPesquisadas) {
            UltimasLojasPesquisadasFragment fragment = (UltimasLojasPesquisadasFragment) activity.getSupportFragmentManager().findFragmentByTag("ultimasLojasFragment");
            if(fragment==null){
                UltimasLojasPesquisadasFragment ultimasLojas = new UltimasLojasPesquisadasFragment();
                ReplaceFragment replaceFragmentObj = new ReplaceFragment(activity);
                replaceFragmentObj.replaceFragment(ultimasLojas, "ultimasLojasFragment");
            }
        } else if (id == R.id.aEquipe) {
            DesenvolvedoresFragment desenvolvedoresFragment = new DesenvolvedoresFragment();
            ReplaceFragment replaceFragmentObj = new ReplaceFragment(activity);
            replaceFragmentObj.criarModal(desenvolvedoresFragment);
        } else if (id == R.id.creditos) {
            CreditosFragment creditos = new CreditosFragment();
            ReplaceFragment replaceFragmentObj = new ReplaceFragment(activity);
            replaceFragmentObj.criarModal(creditos);
        } else if (id == R.id.login) {
            Intent i = new Intent(this.activity, LoginActivity.class);
            activity.startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
