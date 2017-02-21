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
import java.util.zip.Inflater;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Loja;

/**
 * Created by Jocsa on 13/11/2016.
 */

public class ListPesquisaLojaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Loja> lojas;

    public ListPesquisaLojaAdapter(Context ctx, List<Loja> lojas){
        this.ctx = ctx;
        this.lojas = lojas;
    }

    @Override
    public int getCount() {
        return lojas.size();
    }

    @Override
    public Object getItem(int position) {
        return lojas.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(lojas.get(position).getId()==null){
            return 0;
        }
        return lojas.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_list_loja, null);

        Loja loja = lojas.get(position);
        ImageView image = (ImageView) linha.findViewById(R.id.itemFotoDaLoja);

        if(loja.getImagem() != null){
            if(!loja.getImagem().equals("")){
                Log.i("caminhoVazio", loja.getImagem());
                Picasso.with(ctx).load(loja.getImagem()).into(image);
            }
        }

        TextView nomeLoja = (TextView) linha.findViewById(R.id.itemLojaNomeFantasia);
        nomeLoja.setText(loja.getNome_fantasia());
        this.notifyDataSetChanged();
        return linha;
    }

}
