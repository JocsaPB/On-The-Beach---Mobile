package jaj.tct.com.onthebeach.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.Auxiliares.ReplaceFragment;
import jaj.tct.com.onthebeach.Auxiliares.VerificaConexao;
import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.adapters.ListPesquisaPraiasAdapter;
import jaj.tct.com.onthebeach.models.Estado;
import jaj.tct.com.onthebeach.models.Praia;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jocsa on 18/10/16.
 */
public class PesquisaPraia extends Fragment {

    public static final String ESTADO_E_PRAIA = "estadoEPraiaNomes";
    public static final String PRAIA_SELECIONADA = "praiaSelecionada";

    private static final String URL =
            "https://bitbucket.org/Jocsa/jsonauxiliaresotb/raw/8a4e8fcf327d69a231946df8e4b717aaf31814bf/Praias.json";

    private CoordinatorLayout coordinatorLayout;

    private List<Praia> praias;
    private List<Praia> praiasSelecionadas;

    private ListPesquisaPraiasAdapter mAdapter;
    private ListView mListView;
    private Estado estadoSelected;
    private ProgressDialog mProgressDialog;


    private TextView txtFragmentPesquisa;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estadoSelected = (Estado) getArguments().getSerializable(PesquisaEstado.ESTADO_SELECT);
        this.praiasSelecionadas = new ArrayList<>();
        mAdapter = new ListPesquisaPraiasAdapter(getContext(), praiasSelecionadas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesquisa_praia, container, false);

        this.txtFragmentPesquisa = (TextView) v.findViewById(R.id.txtFragmentPesquisaPraias);
        this.txtFragmentPesquisa.setText(this.estadoSelected.getNome() + " > Escolha uma praia:");

        this.mListView = (ListView) v.findViewById(R.id.list_view_praias);
        boolean bancoIsFill = buscarDadosNoBanco();
        if(!bancoIsFill){
            VerificaConexao verificaConexao = new VerificaConexao(getContext());
            if(verificaConexao.existeConexao()){
                mProgressDialog = ProgressDialog.show(getContext(), "Aguarde!", "Carregando as informações", false, false);
                PraiasTasks mTask = new PraiasTasks();
                mTask.execute();
            }
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] arguments = new String[2];
                Praia praia = praiasSelecionadas.get(i);
                arguments[0] = estadoSelected.getNome();
                arguments[1] = praia.getNome();

                Bundle bundle = new Bundle();
                bundle.putStringArray(ESTADO_E_PRAIA, arguments);
                bundle.putSerializable(PRAIA_SELECIONADA, praia);

                PesquisaLoja pesquisaLojasFrag = new PesquisaLoja();
                pesquisaLojasFrag.setArguments(bundle);

                ReplaceFragment replaceFragmentObj = new ReplaceFragment((AppCompatActivity) getContext());
                replaceFragmentObj.replaceFragment(pesquisaLojasFrag, "fragmentLojas");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * ----------------- MY ASYNCTASK --------------------
     */
    public class PraiasTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
        }

        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(URL).build();

            try {
                Response response = client.newCall(request).execute();

                if(response!=null){
                    String json = response.body().string();

                    Gson gson = new Gson();
                    if (!"".equals(json)) {
                        Type listType = new TypeToken<List<Praia>>() {}.getType();
                        praias = gson.fromJson(json, listType);
                    } else {
                        Toast.makeText(getActivity(), "Ocorreu uma falha! Servidor bitbucket não respondeu!", Toast.LENGTH_SHORT).show();
                    }
                    Praia.deleteAll(Praia.class);
                    if (!praias.isEmpty()) {
                        /** Percorrendo as praias por estado*/
                        for (Praia praia : praias) {
                            /** Salvando as praias no banco */
                            praia.save();
                            /** Verifica se o atributo _id_estado é igual ao _id do estado selecionado */
                            if (praia.getEstado() == estadoSelected.get_id()) {
                                praiasSelecionadas.add(praia);
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "Não foi possível carregar os dados, favor verifique sua aconexão!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Não foi possível conectar. Verifique sua conexão!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Não foi possível carregar os dados, favor verifique sua aconexão!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = new ListPesquisaPraiasAdapter(getContext(), praiasSelecionadas);
            mListView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
            mAdapter.notifyDataSetChanged();
        }
    }

    public boolean buscarDadosNoBanco() {
        long id_estado = estadoSelected.get_id();
        praiasSelecionadas = Praia.findWithQuery(
                Praia.class, "Select * from Praia where estado = ?", String.valueOf(id_estado));

        if (!praiasSelecionadas.isEmpty()) {
            mAdapter = new ListPesquisaPraiasAdapter(getContext(), praiasSelecionadas);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

}
