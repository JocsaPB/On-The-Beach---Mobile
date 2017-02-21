package jaj.tct.com.onthebeach.fragments;

import android.app.Dialog;
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

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Produto;

/**
 * Created by Jocsa on 17/11/2016.
 */

public class ProdutoFragment extends DialogFragment {

    public static final String PRODUTO = "produto";

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
        Produto produto = (Produto) getArguments().getSerializable(PRODUTO);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_produto_layout, null);

        ImageView fotoDoProduto = (ImageView) layout.findViewById(R.id.imagemDoProduto);
        if(produto.getImagem()!=null){
            Picasso.with(getContext()).load(produto.getImagem()).into(fotoDoProduto);
        }else{
            Picasso.with(getContext()).load(R.drawable.no_photos_128pixel).into(fotoDoProduto);
        }

        TextView descricao = (TextView) layout.findViewById(R.id.descricaoDoProduto);
        descricao.setText(produto.getDescricao());
        TextView preco = (TextView) layout.findViewById(R.id.precoProduto);
        preco.setText("R$ " + produto.getPreco());
        TextView qnt = (TextView) layout.findViewById(R.id.qntProduto);
        qnt.setText("Quantidade: "+String.valueOf(produto.getQuantidade()));
        if (!produto.isDemanda()) {
            TextView validade = (TextView) layout.findViewById(R.id.validadeProduto);

            //TimeZone tz = TimeZone.getDefault();
            //DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            //df.setTimeZone(tz);
            //tring nowAsIso = df.format(produto.getData_vencimento_oferta());

            validade.setText("Valido até: " + produto.getData_vencimento_oferta());
            LinearLayout layoutValidade = (LinearLayout) layout.findViewById(R.id.layoutValidade);
            layoutValidade.setVisibility(View.VISIBLE);
        } else {
            LinearLayout layoutValidade = (LinearLayout) layout.findViewById(R.id.layoutValidade);
            layoutValidade.setVisibility(View.GONE);
        }
        builder.setView(layout)
                .setTitle(produto.getNome_do_produto());

        return builder.create();
    }
}
