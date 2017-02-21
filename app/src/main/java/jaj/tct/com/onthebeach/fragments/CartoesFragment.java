package jaj.tct.com.onthebeach.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.ListFormaPagamento;

/**
 * Created by Jocsa on 19/11/2016.
 */

public class CartoesFragment extends DialogFragment{

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    private TextView modalidade;
    private String anuncio_modalidade;
    private int modalidadeInt;

    private ListFormaPagamento formaPagamento;

    private CheckBox visa;
    private CheckBox master;
    private CheckBox hiper;
    private CheckBox elo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modalidadeInt = getArguments().getInt("modalidade");
        if(modalidadeInt==1){
            anuncio_modalidade = "Débito";
        }else if(modalidadeInt==2){
            anuncio_modalidade = "Crédito";
        }
        formaPagamento = (ListFormaPagamento) getArguments().getSerializable(LojaFragment.FORMA_PAGAMENTO);
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_cartoes_layout, null);
        modalidade = (TextView) layout.findViewById(R.id.textviewModalidade);
        modalidade.setText("Cartões aceitos na modalidade "+anuncio_modalidade);

        if(modalidadeInt==1){
            visa = (CheckBox) layout.findViewById(R.id.checkboxVisa);
            visa.setChecked(formaPagamento.getDebito().isVisa());
            master = (CheckBox) layout.findViewById(R.id.checkboxMaster);
            master.setChecked(formaPagamento.getDebito().isMaster());
            hiper = (CheckBox) layout.findViewById(R.id.checkboxHiper);
            hiper.setChecked(formaPagamento.getDebito().isHiper());
            elo = (CheckBox) layout.findViewById(R.id.checkboxElo);
            elo.setChecked(formaPagamento.getDebito().isElo());
        }else if(modalidadeInt==2){
            visa = (CheckBox) layout.findViewById(R.id.checkboxVisa);
            visa.setChecked(formaPagamento.getCredito().isVisa());
            master = (CheckBox) layout.findViewById(R.id.checkboxMaster);
            master.setChecked(formaPagamento.getCredito().isMaster());
            hiper = (CheckBox) layout.findViewById(R.id.checkboxHiper);
            hiper.setChecked(formaPagamento.getCredito().isHiper());
            elo = (CheckBox) layout.findViewById(R.id.checkboxElo);
            elo.setChecked(formaPagamento.getCredito().isElo());
        }


        builder.setView(layout);
        return builder.create();
    }
}
