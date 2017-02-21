package jaj.tct.com.onthebeach.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jaj.tct.com.onthebeach.R;

/**
 * Created by Jocsa on 29/11/2016.
 */

public class CreditosFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    private TextView beach;
    private TextView map;
    private TextView store;
    private TextView star;
    private TextView about;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_creditos, null);

        beach = (TextView) linearLayout.findViewById(R.id.creditosPraia);
        beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.flaticon.com/free-icon/beach_201934#term=beach&page=1&position=86");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        map = (TextView) linearLayout.findViewById(R.id.creditosMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.flaticon.com/free-icon/international-delivery_45924#term=map&page=3&position=33");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        store = (TextView) linearLayout.findViewById(R.id.creditosLoja);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.flaticon.com/free-icon/shopping-basket_199565#term=store&page=2&position=13");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        star = (TextView) linearLayout.findViewById(R.id.creditosStar);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.flaticon.com/free-icon/about-us_15659#term=about us&page=1&position=1");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        about = (TextView) linearLayout.findViewById(R.id.creditosAbout);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.flaticon.com/free-icon/star_288510#term=star&page=1&position=35");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        builder.setView(linearLayout);
        return builder.create();
    }
}
