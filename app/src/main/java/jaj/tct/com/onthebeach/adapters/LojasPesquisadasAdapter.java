package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Loja;

/**
 * Created by Jocsa on 21/11/2016.
 */

public class LojasPesquisadasAdapter extends BaseAdapter {

    private Context ctx;
    private List<Loja> lojas;

    public LojasPesquisadasAdapter(Context ctx, List<Loja> lojas) {
        this.ctx = ctx;
        this.lojas = lojas;
    }

    @Override
    public int getCount() {
        return lojas.size();
    }

    @Override
    public Object getItem(int i) {
        return lojas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lojas.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_list_loja, null);
        Loja loja = lojas.get(position);
        ImageView image = (ImageView) linha.findViewById(R.id.itemFotoDaLoja);
        if (loja.getImagem() != null) {
            if (!loja.getImagem().equals("")) {
                Picasso.with(ctx).load(loja.getImagem()).into(image);
            }
        }
        /** CRIAR CÓDIGO PARA RECUPERAR IMAGEM DE UMA URL */
        TextView nomeLoja = (TextView) linha.findViewById(R.id.itemLojaNomeFantasia);
        nomeLoja.setText(loja.getNome_fantasia());
        return linha;
    }

}
