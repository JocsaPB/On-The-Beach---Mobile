package jaj.tct.com.onthebeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Segmento;

/**
 * Created by Jocsa on 14/11/2016.
 */

public class SpinnerSegmentoAdapter extends BaseAdapter{

    private Context ctx;
    private List<Segmento> segmentos;

    public SpinnerSegmentoAdapter(Context ctx, List<Segmento> segmentos){
        this.ctx = ctx;
        this.segmentos = segmentos;
    }

    @Override
    public int getCount() {
        return segmentos.size();
    }

    @Override
    public Object getItem(int i) {
        return segmentos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return segmentos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getDropDownView(i, view, viewGroup);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_spinner_filtro, null);

        TextView textView = (TextView) linha.findViewById(R.id.textItemSpinnerFiltro);
        textView.setText(segmentos.get(position).getSegmento());

        return linha;
    }
}
