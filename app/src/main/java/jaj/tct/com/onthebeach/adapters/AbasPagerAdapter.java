package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.fragments.MapAbrangente;
import jaj.tct.com.onthebeach.fragments.PesquisaEstado;

/**
 * Created by jocsa on 05/10/16.
 */
public class AbasPagerAdapter extends FragmentPagerAdapter {

    String[] titulosAbas;

    public AbasPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        titulosAbas = ctx.getResources().getStringArray(R.array.abas_pager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new PesquisaEstado();
            case 1:
                return new MapAbrangente();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return titulosAbas[position].toUpperCase(l);
    }


}

