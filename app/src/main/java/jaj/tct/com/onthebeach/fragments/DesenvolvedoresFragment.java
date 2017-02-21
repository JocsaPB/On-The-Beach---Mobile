package jaj.tct.com.onthebeach.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import jaj.tct.com.onthebeach.R;

/**
 * Created by Jocsa on 29/11/2016.
 */

public class DesenvolvedoresFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ScrollView linearLayout = (ScrollView) inflater.inflate(R.layout.fragment_desenvolvedores, null);

        builder.setView(linearLayout);

        return builder.create();
    }
}
