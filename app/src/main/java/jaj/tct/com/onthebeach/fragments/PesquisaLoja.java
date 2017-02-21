package jaj.tct.com.onthebeach.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.Auxiliares.ObterLatLng;
import jaj.tct.com.onthebeach.Auxiliares.ReplaceFragment;
import jaj.tct.com.onthebeach.Auxiliares.VerificaConexao;
import jaj.tct.com.onthebeach.MainActivity;
import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.adapters.ListPesquisaLojaAdapter;
import jaj.tct.com.onthebeach.adapters.SpinnerCategoriaAdapter;
import jaj.tct.com.onthebeach.adapters.SpinnerSegmentoAdapter;
import jaj.tct.com.onthebeach.models.Categoria;
import jaj.tct.com.onthebeach.models.Credito;
import jaj.tct.com.onthebeach.models.Debito;
import jaj.tct.com.onthebeach.models.Endereco;
import jaj.tct.com.onthebeach.models.ListFormaPagamento;
import jaj.tct.com.onthebeach.models.Loja;
import jaj.tct.com.onthebeach.models.Praia;
import jaj.tct.com.onthebeach.models.Segmento;
import jaj.tct.com.onthebeach.models.Telefones;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jocsa on 13/11/2016.
 */
public class PesquisaLoja extends Fragment {

    // private static final String URL_LOJAS = "http://pastebin.com/raw/AXyxyxH3";
    public static final String LOJA_OBJ = "loja";

    //private String URL_LOJAS = "http://181.215.101.206:3000/lojas";
    //private String URL_LOJAS = "http://192.168.25.171:3000/lojas";
    private String URL_LOJAS = "http://10.10.155.178:3000/lojas";
    private static final String URL_SPINNER_SEGMENTO =
            "https://bitbucket.org/Jocsa/jsonauxiliaresotb/raw/8a4e8fcf327d69a231946df8e4b717aaf31814bf/Segmento.json";
    private static final String URL_SPINNER_CATEGORIA =
            "https://bitbucket.org/Jocsa/jsonauxiliaresotb/raw/8a4e8fcf327d69a231946df8e4b717aaf31814bf/Categorias.json";

    private ImageView fotoDaLoja;
    private CoordinatorLayout coordinatorLayout;

    private List<Loja> listLojas;
    private List<Segmento> segmentos;
    private List<Categoria> todasCategorias;
    private List<Categoria> categoriasFiltradas;

    private String[] estadoEPraia;
    private TextView praiaSelecionada;
    private Praia praiaSeleciondaObj;

    private ListView mListView;

    private TextView semLojas;
    private View separadorView;

    private LinearLayout layoutSpinnerSegmento;
    private LinearLayout layoutSpinnerCategoria;
    private Spinner spinnerSegmento;
    private Spinner spinnerCategoria;

    private ListPesquisaLojaAdapter mAdapter;
    private SpinnerSegmentoAdapter mSegmentoAdapter;
    private SpinnerCategoriaAdapter mCategoriaAdapter;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Inicializando as listas */
        segmentos = new ArrayList<>();
        categoriasFiltradas = new ArrayList<>();
        todasCategorias = new ArrayList<>();
        listLojas = new ArrayList<>();

        /** Inicializando os adapter com arrays vazios */
        mSegmentoAdapter = new SpinnerSegmentoAdapter(getContext(), segmentos);
        mCategoriaAdapter = new SpinnerCategoriaAdapter(getContext(), categoriasFiltradas);

        /**Recuperando o nome da praia selecionada e obj praia*/
        Bundle bundle = getArguments();
        this.estadoEPraia = new String[2];
        this.estadoEPraia = bundle.getStringArray(PesquisaPraia.ESTADO_E_PRAIA);
        this.praiaSeleciondaObj = (Praia) bundle.getSerializable(PesquisaPraia.PRAIA_SELECIONADA);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesquisa_lojas, container, false);


        /** Inicializando o TextView */
        this.praiaSelecionada = (TextView) v.findViewById(R.id.textPraiaSelected);
        this.praiaSelecionada.setText(estadoEPraia[0] + " > " + estadoEPraia[1]);

        /** Inicializando os container dos spinners e setando a visibilidade*/
        layoutSpinnerSegmento = (LinearLayout) v.findViewById(R.id.layoutSpinnersSegmento);
        layoutSpinnerCategoria = (LinearLayout) v.findViewById(R.id.layoutSpinnersCategoria);

        /** Inicializando os spinners e o listview */
        spinnerSegmento = (Spinner) v.findViewById(R.id.spinnerSegmento);
        spinnerCategoria = (Spinner) v.findViewById(R.id.spinnerCategoria);
        mListView = (ListView) v.findViewById(R.id.listViewLojas);

        /** Inicialiando o ImagemView voltar do spinner e setando um onclicklistener */
        ImageView image1 = (ImageView) v.findViewById(R.id.imagemViewSpinnerCategoria);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean bancoIsFill = buscarSegmentoDb();
                if (!bancoIsFill) {
                    VerificaConexao verificaConexao = new VerificaConexao(getContext());
                    if (verificaConexao.existeConexao()) {
                        /**Chamando o Classe asynctask pra buscar os segmentos*/
                        SpinnerSegmentoTask mSpinnerSegmentoTask = new SpinnerSegmentoTask();
                        mSpinnerSegmentoTask.execute();
                    }
                }
                layoutSpinnerSegmento.setVisibility(View.VISIBLE);
                layoutSpinnerCategoria.setVisibility(View.GONE);
                FindLojas findLojas = new FindLojas();
                findLojas.execute();
            }
        });

        semLojas = (TextView) v.findViewById(R.id.tvSemLojas);
        semLojas.setVisibility(View.GONE);
        separadorView = v.findViewById(R.id.separadorPesquisaLojas);

        /** Rever esta implementação */
        ImageView mapListLojas = (ImageView) v.findViewById(R.id.itemLojaMapButton);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Boolean bancoIsFill = buscarSegmentoDb();
        if (!bancoIsFill) {
            VerificaConexao verificaConexao = new VerificaConexao(getContext());
            if (verificaConexao.existeConexao()) {
                /**Chamando o Classe asynctask pra buscar os segmentos*/
                SpinnerSegmentoTask mSpinnerSegmentoTask = new SpinnerSegmentoTask();
                mSpinnerSegmentoTask.execute();
            }
        }

        /** Setando o listener no spinner de segmentos */
        spinnerSegmento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Segmento segmento = (Segmento) mSegmentoAdapter.getItem(i);
                if (segmento.get_id() != 0) {
                    /** Realiza query no banco e caso retorne false, realiza busca via http */
                    boolean bancoIsFill = buscarCategoriaDb(segmento.get_id());
                    if (!bancoIsFill) {
                        SpinnerCategoriaTask categoriaTask = new SpinnerCategoriaTask();
                        categoriaTask.execute(segmento.get_id());
                    } else {
                        layoutSpinnerCategoria.setVisibility(View.VISIBLE);
                        layoutSpinnerSegmento.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Categoria categoria = (Categoria) mCategoriaAdapter.getItem(i);
                Log.i("idcategoria", "Id da categoria: " + categoria.get_id());
                if (categoria.get_id() != 0) {
                    FindLojas task = new FindLojas();
                    task.execute(categoria.get_id());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        VerificaConexao verificaConexao = new VerificaConexao(getContext());
        if (verificaConexao.existeConexao()) {
            FindLojas lojasTask = new FindLojas();
            lojasTask.execute();
        } else {
            Toast.makeText(getActivity(), "Não foi possível carregar lojas. Verifique sua conexão!", Toast.LENGTH_LONG).show();
        }


        /** Setando listener na lista de lojas */
        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** loja clicada no listview */
                Loja loja = listLojas.get(i);

                /** recuperando o última loja inserida para pegar uma chave única auxiliar nomeada de iddobanco */
                Loja ultima_loja_inserida = Loja.last(Loja.class);
                Long idDaUltimaLojaNoBanco = 0L;
                if (ultima_loja_inserida != null) {
                    if (loja.getIdl().equals(ultima_loja_inserida.getIdl())) {
                        LojaFragment lojaFragment = new LojaFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(LOJA_OBJ, loja);
                        lojaFragment.setArguments(bundle);

                        ReplaceFragment replaceFragmentObj = new ReplaceFragment((AppCompatActivity) getContext());
                        replaceFragmentObj.replaceFragment(lojaFragment, "fragmentLoja");
                        return;
                    }
                    idDaUltimaLojaNoBanco = ultima_loja_inserida.getIddobanco();
                }

                /** verifica se a loja clicada já existe no banco , se existe exclui ela no próximo IF*/
                List<Loja> lista = Loja.find(Loja.class, "idl = ?", loja.getIdl());
                if (!lista.isEmpty()) {
                    Loja existLoja = lista.get(0);
                    if (existLoja != null) {
                        Loja.delete(existLoja);
                    }
                }

                /** seta o novo iddobanco na loja
                 *  ps: o iddobanco é uma 'chave única' auxiliar na tabela e não uma primary key*/
                loja.setIddobanco(idDaUltimaLojaNoBanco + 1);
                /** Salvando loja no banco */
                for (Telefones telefone : loja.getNumero_telefone()){
                    telefone.setIdloja(loja.getIdl());
                    Telefones.save(telefone);
                }
                loja.getFormas_pagamentos().setIdloja(loja.getIdl());
                ListFormaPagamento.save(loja.getFormas_pagamentos());
                    loja.getFormas_pagamentos().getDebito().setIdformapagamento(loja.getFormas_pagamentos().getId());
                    loja.getFormas_pagamentos().getCredito().setIdformapagamento(loja.getFormas_pagamentos().getId());
                    Debito.save(loja.getFormas_pagamentos().getDebito());
                    Credito.save(loja.getFormas_pagamentos().getCredito());
                Endereco.save(loja.getEndereco());
                Loja.save(loja);

                LojaFragment lojaFragment = new LojaFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(LOJA_OBJ, loja);
                lojaFragment.setArguments(bundle);

                ReplaceFragment replaceFragmentObj = new ReplaceFragment((AppCompatActivity) getContext());
                replaceFragmentObj.replaceFragment(lojaFragment, "fragmentLoja");
            }

        });


    }

    /**
     * ----------------- ASSYNC TASK BUSCA LOJAS-----------------
     */
    public class FindLojas extends AsyncTask<Long, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(getContext(), "Aguarde!", "Buscando lojas...", false, false);
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
        }

        @Override
        protected Long doInBackground(final Long... longs) {
            OkHttpClient client = new OkHttpClient();

            if (longs.length == 0) {
                URL_LOJAS += "/praia/" + praiaSeleciondaObj.get_id();
            } else {
                URL_LOJAS += "/praia/" + praiaSeleciondaObj.get_id() + "/categoria/" + longs[0];
            }

            Request request = new Request.Builder().url(URL_LOJAS).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /** GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
             *  The Gson instance created will exclude all fields in a class that are not marked with @Expose annotation.
             */
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String jsonGet = null;

            if (response != null) {
                try {
                    jsonGet = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("url", "A url foi: " + URL_LOJAS);
                Log.i("url", jsonGet);
                if (jsonGet.equals("{\"error\":\"Nenhum token enviado\"}")) {
                    return null;
                }
                if (jsonGet.isEmpty() || jsonGet.contains("error")) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Ocorreu um erro interto! :( por favor tente novamente!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (jsonGet.equals("[]")) {
                    if (longs.length > 0) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Não existe lojas para esta categoria, ainda!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        /** Setando o array vazio pois não há lojas */
                        listLojas = new ArrayList<>();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Não existe lojas para esta praia, ainda!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    Type listType = new TypeToken<List<Loja>>() {
                    }.getType();
                    listLojas = gson.fromJson(jsonGet, listType);

                }
            } else {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Não foi possível conectar. Verifique sua conexão!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }


            if (longs.length == 0) {
                return null;
            } else {
                return longs[0];
            }
        }

        @Override
        protected void onPostExecute(Long idCategoria) {
            super.onPostExecute(idCategoria);
            mAdapter = new ListPesquisaLojaAdapter(getContext(), listLojas);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            URL_LOJAS = "http://10.10.155.178:3000/lojas";
            if(pDialog != null)
              pDialog.dismiss();

            if (listLojas.isEmpty()) {
                if (idCategoria == null) {
                    semLojas.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    layoutSpinnerSegmento.setVisibility(View.GONE);
                    separadorView.setVisibility(View.GONE);
                } else {
                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Não existe loja cadastrada para esta categoria, ainda.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }else{
                if (idCategoria == null) {
                    semLojas.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    layoutSpinnerSegmento.setVisibility(View.VISIBLE);
                    separadorView.setVisibility(View.VISIBLE);
                }else{
                    semLojas.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    layoutSpinnerSegmento.setVisibility(View.GONE);
                    layoutSpinnerCategoria.setVisibility(View.VISIBLE);
                    separadorView.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    /**
     * ----------------- ASSYNC TASK BUSCA SEGMENTOS-----------------
     */
    public class SpinnerSegmentoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();

            try {
                Request request_segmentos = new Request.Builder().url(URL_SPINNER_SEGMENTO).build();
                Response response_segmentos = client.newCall(request_segmentos).execute();
                if(response_segmentos!=null){
                    String jsonGetSegmentos = response_segmentos.body().string();

                    if (!"".equals(jsonGetSegmentos)) {
                        Type listTypeSegmentos = new TypeToken<List<Segmento>>() {
                        }.getType();
                        segmentos = gson.fromJson(jsonGetSegmentos, listTypeSegmentos);
                    } else {
                        Toast.makeText(getActivity(), "Não foi possível carregar as categorias! Verifique sua conexao!", Toast.LENGTH_SHORT).show();
                    }

                    Segmento filtrePorCategoria = new Segmento();
                    filtrePorCategoria.setId(0L);
                    filtrePorCategoria.setSegmento("Filtre por categoria!");
                    segmentos.add(0, filtrePorCategoria);

                    if (!segmentos.isEmpty()) {
                        /** Deletando segmentos existentes e salvando os novos recebidos */
                        Segmento.deleteAll(Segmento.class);
                        for (Segmento seg : segmentos) {
                            seg.save();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Não foi possível carregar as categorias! Verifique sua conexao!", Toast.LENGTH_SHORT).show();
                    }
                    Segmento.save(filtrePorCategoria);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Não foi possível conectar. Verifique sua conexão!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSegmentoAdapter = new SpinnerSegmentoAdapter(getContext(), segmentos);
            spinnerSegmento.setAdapter(mSegmentoAdapter);
            mSegmentoAdapter.notifyDataSetChanged();
            if(!listLojas.isEmpty()){
                layoutSpinnerSegmento.setVisibility(View.VISIBLE);
            }
            layoutSpinnerCategoria.setVisibility(View.GONE);

        }
    }

    /**
     * ----------------- ASSYNC TASK BUSCA CATEGORIAS-----------------
     */
    public class SpinnerCategoriaTask extends AsyncTask<Long, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
        }

        @Override
        protected Void doInBackground(Long... _id_segmento) {
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();

            try {
                Request request_categorias = new Request.Builder().url(URL_SPINNER_CATEGORIA).build();
                Response response_categorias = client.newCall(request_categorias).execute();

                if(response_categorias!=null){
                    String jsonGetCategorias = response_categorias.body().string();

                    if (!"".equals(jsonGetCategorias)) {
                        Type listTypeCategorias = new TypeToken<List<Categoria>>() {
                        }.getType();
                        todasCategorias = gson.fromJson(jsonGetCategorias, listTypeCategorias);
                    } else {
                        Toast.makeText(getActivity(), "Não foi possível carregar as categorias! Verifique sua conexao!", Toast.LENGTH_SHORT).show();
                    }

                    if (!todasCategorias.isEmpty()) {

                        /** Deletando categorias existentes e salvandos as novas recebidas via http */
                        Categoria.deleteAll(Categoria.class);
                        Categoria categoria = new Categoria();
                        categoria.setId(0L);
                        categoria.setCategoria("Selecione uma subcategoria");
                        categoria.set_id_segmento(0);
                        categoriasFiltradas.add(categoria);
                        for (Categoria categ : todasCategorias) {
                            categ.save();
                            if (_id_segmento[0].equals(categ.get_id_segmento())) {
                                categoriasFiltradas.add(categ);
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Não foi possível carregar as categorias! Verifique sua conexao!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Não foi possível conectar. Verifique sua conexão!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCategoriaAdapter = new SpinnerCategoriaAdapter(getContext(), categoriasFiltradas);
            spinnerCategoria.setAdapter(mCategoriaAdapter);
            mCategoriaAdapter.notifyDataSetChanged();
            layoutSpinnerCategoria.setVisibility(View.VISIBLE);
            layoutSpinnerSegmento.setVisibility(View.GONE);
        }
    }

    private boolean buscarSegmentoDb() {
        segmentos = Segmento.listAll(Segmento.class);
        if (!segmentos.isEmpty()) {
            mSegmentoAdapter = new SpinnerSegmentoAdapter(getContext(), segmentos);
            spinnerSegmento.setAdapter(mSegmentoAdapter);
            mSegmentoAdapter.notifyDataSetChanged();
            if(!listLojas.isEmpty()){
                layoutSpinnerSegmento.setVisibility(View.VISIBLE);
            }
            layoutSpinnerCategoria.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    private boolean buscarCategoriaDb(long _id_segmento) {
        categoriasFiltradas = Categoria.findWithQuery(
                Categoria.class, "Select * from Categoria where segmento = ?", String.valueOf(_id_segmento)
        );


        if (!categoriasFiltradas.isEmpty() || categoriasFiltradas.size() > 0) {
            Categoria categoria = new Categoria();
            categoria.setId(0L);
            categoria.setCategoria("Selecione uma subcategoria");
            categoria.set_id_segmento(0);
            categoriasFiltradas.add(0, categoria);
            mCategoriaAdapter = new SpinnerCategoriaAdapter(getContext(), categoriasFiltradas);
            spinnerCategoria.setAdapter(mCategoriaAdapter);
            mCategoriaAdapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }

}
