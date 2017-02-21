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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Telefones;

/**
 * Created by Jocsa on 19/11/2016.
 */

public class TelefonesFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    private ImageView ivTelefone1;
    private ImageView ivTelefone2;
    private ImageView ivTelefone3;
    private TextView tvTelefone1;
    private TextView tvTelefone2;
    private TextView tvTelefone3;

    private List<String> telefonesProntoParaLigacao;
    private List<Telefones> telefones;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();

        telefonesProntoParaLigacao = new ArrayList<>();
        telefonesProntoParaLigacao = getArguments().getStringArrayList(LojaFragment.TELEFONES_PRONTOS_PARALIGACAO);
        telefones = new ArrayList<>();
        telefones = (ArrayList) getArguments().getSerializable(LojaFragment.TELEFONES);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_numeros_telefones, null);

        /** SETANDO TELEFONE 1 */
        ivTelefone1 = (ImageView) linearLayout.findViewById(R.id.ligarParaLojaTel1);
        tvTelefone1 = (TextView) linearLayout.findViewById(R.id.numeroTelefone1);
        tvTelefone1.setText(telefones.get(0).getUm());

        /** Setando intent ligação para o telefone 1 */
        ivTelefone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+telefonesProntoParaLigacao.get(0));
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                getActivity().startActivity(intent);
            }
        });

        ivTelefone2 = (ImageView) linearLayout.findViewById(R.id.ligarParaLojaTel2);
        ivTelefone2.setVisibility(View.GONE);
        tvTelefone2 = (TextView) linearLayout.findViewById(R.id.numeroTelefone2);
        tvTelefone2.setVisibility(View.GONE);
        if(telefones.get(0).getDois() != null){
                /** SETANDO TELEFONE 2 */
                ivTelefone2.setVisibility(View.VISIBLE);
                tvTelefone2.setVisibility(View.VISIBLE);
                tvTelefone2.setText(telefones.get(0).getDois());

                /** Setando intent ligação para o telefone 2 */
                ivTelefone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("tel:"+telefonesProntoParaLigacao.get(1));
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        getActivity().startActivity(intent);
                    }
            });

        }

        ivTelefone3 = (ImageView) linearLayout.findViewById(R.id.ligarParaLojaTel3);
        ivTelefone3.setVisibility(View.GONE);
        tvTelefone3 = (TextView) linearLayout.findViewById(R.id.numeroTelefone3);
        tvTelefone3.setVisibility(View.GONE);
        if(telefones.get(0).getTres()!=null){
                /** SETANDO TELEFONE 3 */
                tvTelefone3.setVisibility(View.VISIBLE);
                ivTelefone3.setVisibility(View.VISIBLE);
                tvTelefone3.setText(telefones.get(0).getTres());

                /** Setando intent ligação para o telefone 3 */
                ivTelefone3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("tel:"+telefonesProntoParaLigacao.get(2));
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        getActivity().startActivity(intent);
                    }
                });

        }

        builder.setView(linearLayout);

        return builder.create();
    }

}
