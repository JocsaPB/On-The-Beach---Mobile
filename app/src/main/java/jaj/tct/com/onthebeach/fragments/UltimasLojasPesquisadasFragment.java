package jaj.tct.com.onthebeach.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.Auxiliares.ReplaceFragment;
import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.adapters.LojasPesquisadasAdapter;
import jaj.tct.com.onthebeach.models.Loja;

/**
 * Created by Jocsa on 29/11/2016.
 */

public class UltimasLojasPesquisadasFragment extends Fragment {

    public static final String LOJA_OBJ = "loja";

    private ListView mListView;
    private LojasPesquisadasAdapter lojasPesquisadasAdapter;

    private List<Loja> listLoja;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listLoja = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ultimas_lojas_pesquisadas, container, false);

        mListView = (ListView) v.findViewById(R.id.listviewUltimasLojasPesquisadas);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buscarNoBancoLojasPesquisadas();

        /** Setando listener na lista de lojas */
        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** loja clicada no listview */
                Loja loja = listLoja.get(i);

                LojaFragment lojaFragment = new LojaFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(LOJA_OBJ, loja);
                lojaFragment.setArguments(bundle);

                ReplaceFragment replaceFragmentObj = new ReplaceFragment((AppCompatActivity) getContext());
                replaceFragmentObj.replaceFragment(lojaFragment, "fragmentLoja");
            }

        });

    }

    public void buscarNoBancoLojasPesquisadas() {
        listLoja = Loja.find(Loja.class, "1=1", null, null, "iddobanco DESC", "20");

        if(!listLoja.isEmpty()){
            lojasPesquisadasAdapter = new LojasPesquisadasAdapter(getContext(), listLoja);
            mListView.setAdapter(lojasPesquisadasAdapter);
            lojasPesquisadasAdapter.notifyDataSetChanged();
        }
    }
}
