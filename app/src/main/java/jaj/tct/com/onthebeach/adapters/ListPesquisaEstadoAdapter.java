package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Estado;

/**
 * Created by jocsa on 15/10/16.
 */
public class ListPesquisaEstadoAdapter extends BaseAdapter {

    private Context ctx;
    private List<Estado> estados;

    public ListPesquisaEstadoAdapter(Context ctx, List<Estado> estados){
        this.ctx = ctx;
        this.estados = estados;
    }

    @Override
    public int getCount() {
        return estados.size();
    }

    @Override
    public Object getItem(int position) {
        return estados.get(position);
    }

    @Override
    public long getItemId(int position) {
        Estado estado = this.estados.get(position);
        return estado.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estado estado = this.estados.get(position);

        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_list_pesquisa, null);

        TextView txtNomeEstado = (TextView) linha.findViewById(R.id.txtItemPesquisa);

        txtNomeEstado.setText(estado.getNome());

        this.notifyDataSetChanged();
        return linha;
    }

}
