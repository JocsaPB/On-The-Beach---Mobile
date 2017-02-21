package jaj.tct.com.onthebeach.Auxiliares;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.fragments.CreditosFragment;

/**
 * Created by Jocsa on 13/11/2016.
 */

public class ReplaceFragment <Contexto extends AppCompatActivity> {

    Contexto ctx;

    public ReplaceFragment(Contexto ctx){
        this.ctx = ctx;
    }

    public void replaceFragment(Fragment fragment, String fragmentToBack) {

        android.support.v4.app.FragmentTransaction ft = ctx.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, fragmentToBack);
        ft.addToBackStack("add"+fragmentToBack);
        ft.commit();
    }

    public void criarModal(DialogFragment fragment){
        fragment.show(ctx.getSupportFragmentManager().beginTransaction(), "FragmentCredito");
    }

}
