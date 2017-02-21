package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Praia;

/**
 * Created by jocsa on 18/10/16.
 */
public class ListPesquisaPraiasAdapter extends BaseAdapter {

    private Context ctx;
    private List<Praia> praias;

    public ListPesquisaPraiasAdapter(Context ctx, List<Praia> praias) {
        this.ctx = ctx;
        this.praias = praias;
    }

    @Override
    public int getCount() {
        return praias.size();
    }

    @Override
    public Object getItem(int position) {
        return praias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return praias.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_list_pesquisa, null);

        Praia praia = this.praias.get(position);
        TextView textViewPraia = (TextView) linha.findViewById(R.id.txtItemPesquisa);
        textViewPraia.setText(praia.getNome());
        this.notifyDataSetChanged();
        return linha;
    }


}
