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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import jaj.tct.com.onthebeach.adapters.ListPesquisaEstadoAdapter;
import jaj.tct.com.onthebeach.models.Estado;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jocsa on 15/10/16.
 */
public class PesquisaEstado extends Fragment {

    private static final String URL =
            "https://bitbucket.org/Jocsa/jsonauxiliaresotb/raw/8a4e8fcf327d69a231946df8e4b717aaf31814bf/Estados.json";

    public static final String ESTADO_SELECT = "estado";

    private CoordinatorLayout coordinatorLayout;

    private List<Estado> estados;
    private ListView mListView;

    private ListPesquisaEstadoAdapter mAdapter;
    
    private TextView txtFragmentPesquisa;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        estados = new ArrayList<>();
        mAdapter = new ListPesquisaEstadoAdapter(getContext(), estados);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesquisa_estado, container, false);

        this.txtFragmentPesquisa = (TextView) v.findViewById(R.id.txtFragmentPesquisaEstado);
        this.txtFragmentPesquisa.setText("Selecione um estado:");

        mListView = (ListView) v.findViewById(R.id.list_view_estado);

        boolean bancoIsFill = buscarDadosNoBanco();

        if(!bancoIsFill){
            VerificaConexao verificaConexao = new VerificaConexao(getContext());
            if(verificaConexao.existeConexao()){
                mProgressDialog = ProgressDialog.show(getContext(), "Aguarde!", "Carregando as informações", false, false);
                EstadosTask mTask = new EstadosTask();
                mTask.execute();
            }else{
                Toast.makeText(getActivity(), "Não foi possível carregar os dados. Favor, verifique sua conexão!", Toast.LENGTH_LONG).show();
            }
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estado estado = (Estado) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ESTADO_SELECT,estado);
                PesquisaPraia p = new PesquisaPraia();
                p.setArguments(bundle);
                ReplaceFragment replaceFragmentObj = new ReplaceFragment((AppCompatActivity) getContext());
                replaceFragmentObj.replaceFragment(p, "fragmentPraia");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**----------------- ESTADO ASYNCTASK --------------------*/
    public class EstadosTask extends AsyncTask<Void, Void, Void> {

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
                    if(!"".equals(json)){
                        Type listType = new TypeToken<List<Estado>>() {}.getType();

                        estados = gson.fromJson(json, listType);
                        Estado.deleteAll(Estado.class);

                        if(!estados.isEmpty()){
                            /** Salvando os estados no banco */
                            for (Estado estado : estados){
                                estado.save();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Ocorreu uma falha! Verifique sua conexão!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Ocorreu uma falha! Servidor bitbucket não respondeu!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Não foi possível conectar. Verifique sua conexão!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Ocorreu uma falha! Verifique sua conexão!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new ListPesquisaEstadoAdapter(getContext(), estados);
            mListView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
            mAdapter.notifyDataSetChanged();
        }
    }


    public boolean buscarDadosNoBanco(){
        estados = Estado.listAll(Estado.class);
        if(estados.size()>=1){
            mAdapter = new ListPesquisaEstadoAdapter(getContext(), estados);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

}



