package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Categoria;

/**
 * Created by Jocsa on 14/11/2016.
 */

public class SpinnerCategoriaAdapter extends BaseAdapter{

    private Context ctx;
    private List<Categoria> categorias;

    public SpinnerCategoriaAdapter(Context ctx, List<Categoria> categorias) {
        this.ctx = ctx;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int i) {
        return categorias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categorias.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       return  getDropDownView(i, view, viewGroup);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_spinner_filtro, null);

        TextView textView = (TextView) linha.findViewById(R.id.textItemSpinnerFiltro);
        textView.setText(categorias.get(position).getCategoria());

        return linha;
    }
}
