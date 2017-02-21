package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jocsa on 13/10/16.
 */
public class Loja extends SugarRecord implements Serializable {

    @Expose
    @SerializedName("_id")
    private String idl;
    @Expose
    private String razao_social;
    @Expose
    private String nome_fantasia;
    @Expose
    @Ignore
    private ArrayList<Telefones> numero_telefone;
    @Expose
    private Endereco endereco;
    @Expose
    private ListFormaPagamento formas_pagamentos;
    @Expose
    private long _id_categoria;
    @Expose
    private String _id_lojista;
    @Expose
    private String imagem;
    @Expose
    private String descricao;

    /** Este dado é criado automaticamente no mongo para controle e não será usado na aplicação mobile */
    @Expose
    @Ignore
    private int __v;

    /** ID é utilizado para ordenadar insersão das lojas pelo usuário, quando o mesmo seleciona uma loja
     * para ser utilizado na activity últimas lojas pesquisadas */
    private long iddobanco;

    public String getIdl() {
        return idl;
    }

    public void setIdl(String idl) {
        this.idl = idl;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public ArrayList<Telefones> getNumero_telefone() {
        return numero_telefone;
    }

    public void setNumero_telefone(ArrayList<Telefones> numero_telefone) {
        this.numero_telefone = numero_telefone;
    }

    public ListFormaPagamento getFormas_pagamentos() {
        return formas_pagamentos;
    }

    public void setFormas_pagamentos(ListFormaPagamento formas_pagamentos) {
        this.formas_pagamentos = formas_pagamentos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public long get_id_categoria() {
        return _id_categoria;
    }

    public void set_id_categoria(long _id_categoria) {
        this._id_categoria = _id_categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String get_id_lojista() {
        return _id_lojista;
    }

    public void set_id_lojista(String _id_lojista) {
        this._id_lojista = _id_lojista;
    }

    public long getIddobanco() {
        return iddobanco;
    }

    public void setIddobanco(long iddobanco) {
        this.iddobanco = iddobanco;
    }


}
