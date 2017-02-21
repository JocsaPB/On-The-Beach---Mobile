package jaj.tct.com.onthebeach.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.Auxiliares.ObterLatLng;
import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models.Credito;
import jaj.tct.com.onthebeach.models.Debito;
import jaj.tct.com.onthebeach.models.ListFormaPagamento;
import jaj.tct.com.onthebeach.models.Loja;
import jaj.tct.com.onthebeach.models.Produto;
import jaj.tct.com.onthebeach.models.Telefones;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jocsa on 17/11/2016.
 */

public class LojaFragment extends Fragment {

    public static final String FORMA_PAGAMENTO = "formaPagamento";
    public static final String TELEFONES_PRONTOS_PARALIGACAO = "telefonesPontoParaLigacao";
    public static final String TELEFONES = "telefones";

    //private static final String URL_PRODUTOS = "http://192.168.25.171:3000/produtos/loja/";
    //private static final String URL_PRODUTOS = "http://192.168.25.171:3000/produtos/loja/";
    private static final String URL_PRODUTOS = "http://10.10.155.178:3000/produtos/loja/";
    private CoordinatorLayout coordinatorLayout;

    private List<Produto> produtos;

    private ImageView logoDaloja;
    private ImageView product1;
    private ImageView product2;
    private ImageView product3;
    private TextView textoParaLojaSemProdutos;
    private ImageView botaoMap;

    private TextView telefones;
    private ImageView telefonePrincipal;
    private ArrayList<String> telefonesCorrigidoParaLigacao;

    private TextView nomeFantasia;
    private TextView descricao;

    private CheckBox checkboxDebito;
    private CheckBox checkboxCredito;
    private CheckBox checkboxDinheiro;
    private CheckBox checkboxOutrasFormas;

    private Loja loja;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        produtos = new ArrayList<>();
        telefonesCorrigidoParaLigacao = new ArrayList<>();
        loja = (Loja) getArguments().getSerializable(PesquisaLoja.LOJA_OBJ);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loja, container, false);

        botaoMap = (ImageView) v.findViewById(R.id.mapaDaLoja);
        product1 = (ImageView) v.findViewById(R.id.produto01);
        product1.setVisibility(View.GONE);
        product2 = (ImageView) v.findViewById(R.id.produto02);
        product2.setVisibility(View.GONE);
        product3 = (ImageView) v.findViewById(R.id.produto03);
        product3.setVisibility(View.GONE);
        textoParaLojaSemProdutos = (TextView) v.findViewById(R.id.textoParaLojaSemProdutos);

        telefonePrincipal = (ImageView) v.findViewById(R.id.ligarParaLoja);

        /**FOTO DA LOJA*/
        logoDaloja = (ImageView) v.findViewById(R.id.fotoDaLoja);
        if(loja.getImagem() != null){
            if(!loja.getImagem().equals("")){
                Log.i("caminhoVazio", loja.getImagem());
                Picasso.with(getContext()).load(loja.getImagem()).into(logoDaloja);
            }
        }

        /**NOME FANTASIA*/
        nomeFantasia = (TextView) v.findViewById(R.id.nomeDaLoja);
        nomeFantasia.setText(loja.getNome_fantasia());

        /**TELEFONE PRINCIPAL*/
        if(loja.getNumero_telefone()==null){
            /** Código só é executado quando a loja é buscada do banco
             * No caso normal os dados vão vir do servidor via JSON */
            loja.setNumero_telefone((ArrayList<Telefones>) Telefones.find(Telefones.class, "idloja = ?", loja.getIdl()));
            List<ListFormaPagamento> pag = ListFormaPagamento.find(ListFormaPagamento.class, "idloja= ? ", loja.getIdl());
                loja.setFormas_pagamentos((pag.get(0)));
            Long id = loja.getFormas_pagamentos().getId();
            List<Debito> deb = Debito.find(Debito.class, "idformapagamento = ?", String.valueOf(id));
                loja.getFormas_pagamentos().setDebito(deb.get(0));
            List<Credito> cred = Credito.find(Credito.class, "idformapagamento = ?",String.valueOf(id));
            loja.getFormas_pagamentos().setCredito(cred.get(0));
        }
        telefones = (TextView) v.findViewById(R.id.telefoneDaLoja);
        telefones.setText(loja.getNumero_telefone().get(0).getUm());
        telefonesCorrigidoParaLigacao.add(0,loja.getNumero_telefone().get(0).getUm().replace("(","").replace(")","").replace("-",""));
        if(loja.getNumero_telefone().get(0).getDois()!=null){
            telefonesCorrigidoParaLigacao.add(1,loja.getNumero_telefone().get(0).getDois().replace("(","").replace(")","").replace("-",""));
        }
        if(loja.getNumero_telefone().get(0).getTres()!=null){
            telefonesCorrigidoParaLigacao.add(2,loja.getNumero_telefone().get(0).getTres().replace("(","").replace(")","").replace("-",""));
        }

        /**DESCRICAO*/
        descricao = (TextView) v.findViewById(R.id.descricaoSobreLoja);
        descricao.setText(loja.getDescricao());

        /**FORMAS PAGAMENTO*/
        if(true){

        }
        checkboxDebito = (CheckBox) v.findViewById(R.id.checkboxDebito);
        checkboxDebito.setChecked(isDebitoUsado());
        checkboxCredito = (CheckBox) v.findViewById(R.id.checkboxCredito);
        checkboxCredito.setChecked(isCreditoUsado());
        checkboxDinheiro = (CheckBox) v.findViewById(R.id.checkboxDinheiro);
        checkboxDinheiro.setChecked(loja.getFormas_pagamentos().isDinheiro());
        checkboxOutrasFormas = (CheckBox) v.findViewById(R.id.checkboxOutrasFormas);
        checkboxOutrasFormas.setChecked(loja.getFormas_pagamentos().isOutras());

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Setando listener no produto 1 */
        product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment product01 = new ProdutoFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(ProdutoFragment.PRODUTO, produtos.get(0));
                product01.setArguments(bundle);

                product01.show(getFragmentManager(), "produto01");

            }
        });

        /** Setando listener no produto 2 */
        product2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment product02 = new ProdutoFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(ProdutoFragment.PRODUTO, produtos.get(1));
                product02.setArguments(bundle);

                product02.show(getFragmentManager(), "produto02");
            }
        });


        /** Setando listener no produto 3 */
        product3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment product03 = new ProdutoFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(ProdutoFragment.PRODUTO, produtos.get(2));
                product03.setArguments(bundle);

                product03.show(getFragmentManager(), "produto03");
            }
        });

        checkboxDebito.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isDebitoUsado()) {
                    Bundle bundle = new Bundle();
                    /**1 = débito*/
                    bundle.putInt("modalidade", 1);
                    bundle.putSerializable(FORMA_PAGAMENTO, loja.getFormas_pagamentos());
                    CartoesFragment cartoesDebito = new CartoesFragment();
                    cartoesDebito.setArguments(bundle);
                    cartoesDebito.show(getFragmentManager(), "cartoesDebito");
                    return false;
                } else {
                    Toast.makeText(getActivity(), "Modalidade Débito não aceita!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        });


        /** Setando listener no checkbox de cartao de credito */
        checkboxCredito.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isCreditoUsado()) {
                    Bundle bundle = new Bundle();
                    /**2 = crédito*/
                    bundle.putInt("modalidade", 2);
                    bundle.putSerializable(FORMA_PAGAMENTO, loja.getFormas_pagamentos());
                    CartoesFragment cartoesCredito = new CartoesFragment();
                    cartoesCredito.setArguments(bundle);
                    cartoesCredito.show(getFragmentManager(), "cartoesDebito");
                    return false;
                } else {
                    Toast.makeText(getActivity(), "Modalidade Crédito não aceita!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        });

        if(telefonesCorrigidoParaLigacao.size() > 1){
            /** Setando listener no telefone para mostrar outros telefones */
            telefones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TelefonesFragment telefonesFragment = new TelefonesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(TELEFONES_PRONTOS_PARALIGACAO, telefonesCorrigidoParaLigacao);
                    bundle.putSerializable(TELEFONES, loja.getNumero_telefone());
                    telefonesFragment.setArguments(bundle);
                    telefonesFragment.show(getFragmentManager(), "Telefones da loja");
                }
            });
        }

        /** Setando intent para ligação no telefone principal  */
        telefonePrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+telefonesCorrigidoParaLigacao.get(0));
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        botaoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObterLatLng obterLatLng = new ObterLatLng(loja.getEndereco(), (AppCompatActivity) getContext());
            }
        });



        Log.i("debugs","onVieCreated()");
        BuscarProdutosTask buscarProdutos = new BuscarProdutosTask();
        buscarProdutos.execute();

    }

    private boolean isCreditoUsado(){
        if(loja.getFormas_pagamentos()!=null) {
            if (loja.getFormas_pagamentos().getCredito() != null) {
                if (loja.getFormas_pagamentos().getCredito().isVisa()) {
                    return true;
                } else if (loja.getFormas_pagamentos().getCredito().isMaster()) {
                    return true;
                } else if (loja.getFormas_pagamentos().getCredito().isHiper()) {
                    return true;
                } else if (loja.getFormas_pagamentos().getCredito().isElo()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDebitoUsado(){
        if(loja.getFormas_pagamentos()!=null){
            if(loja.getFormas_pagamentos().getDebito()!=null){
                if(loja.getFormas_pagamentos().getDebito().isVisa()){
                    return true;
                }else if(loja.getFormas_pagamentos().getDebito().isMaster()){
                    return true;
                }else if(loja.getFormas_pagamentos().getDebito().isHiper()){
                    return true;
                }else if(loja.getFormas_pagamentos().getDebito().isElo()){
                    return true;
                }
            }
        }
        return false;
    }


    public class BuscarProdutosTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.myCoordinator);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            /** GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
             *  The Gson instance created will exclude all fields in a class that are not marked with @Expose annotation.
             */
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            Request request = new Request.Builder().url(URL_PRODUTOS+loja.getIdl()).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonGet = "";
            try {
                if(response!=null){
                    jsonGet = response.body().string();
                    if (jsonGet.isEmpty() || jsonGet.contains("error")) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Ocorreu um erro interto! :( por favor tente novamente!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else {
                        Type listType = new TypeToken<List<Produto>>() {}.getType();
                        Log.i("produtosJson", jsonGet);
                        produtos = gson.fromJson(jsonGet, listType);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(jsonGet.equals("{\"error\":\"Nenhum token enviado\"}")){
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            inserirProdutos();
        }
    }

    private void inserirProdutos(){
        Log.i("produtosJson","1 - Tamanho da lista produtos: "+produtos.size());
        if(produtos.size()>0) {
            Log.i("produtosJson","Produtos: "+produtos.toString());
            if (produtos.get(0) != null) {
                Log.i("produtosJson","url 1: "+produtos.get(0).getImagem());
                if (produtos.get(0).getImagem() != null) {
                    Picasso.with(getContext()).load(produtos.get(0).getImagem()).into(product1);
                    product1.setVisibility(View.VISIBLE);
                }
            }
            if(produtos.size()>1) {
                if (produtos.get(1) != null) {
                    Log.i("produtosJson","url 2: "+produtos.get(1).getImagem());
                    if (produtos.get(1).getImagem() != null) {
                        Picasso.with(getContext()).load(produtos.get(1).getImagem()).into(product2);
                        product2.setVisibility(View.VISIBLE);
                    }
                }
                if(produtos.size()>2){
                    if(produtos.get(2)!=null){
                        Log.i("produtosJson","url 3: "+produtos.get(2).getImagem());
                        if(produtos.get(2).getImagem() != null){
                            Picasso.with(getContext()).load(produtos.get(2).getImagem()).into(product3);
                            product3.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }else {
            textoParaLojaSemProdutos.setVisibility(View.VISIBLE);
        }
    }

}
