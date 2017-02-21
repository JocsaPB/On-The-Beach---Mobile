package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Jocsa on 11/11/2016.
 */

public class Produto extends SugarRecord implements Serializable{

    @Expose
    @SerializedName("_id")
    private String idp;
    @Expose
    private String nome_do_produto;
    @Expose
    private boolean demanda;
    @Expose
    private String preco;
    @Expose
    private String data_vencimento_oferta;
    @Expose
    private String imagem;
    @SerializedName("_id_loja")
    private List<String> idloja;
    @Expose
    private String descricao;
    @Expose
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome_do_produto() {
        return nome_do_produto;
    }

    public void setNome_do_produto(String nome_do_produto) {
        this.nome_do_produto = nome_do_produto;
    }

    public boolean isDemanda() {
        return demanda;
    }

    public void setDemanda(boolean demanda) {
        this.demanda = demanda;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getData_vencimento_oferta() {
        return data_vencimento_oferta;
    }

    public void setData_vencimento_oferta(String data_vencimento_oferta) {
        this.data_vencimento_oferta = data_vencimento_oferta;
    }

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public List<String> getIdloja() {
        return idloja;
    }

    public void setIdloja(List<String> idloja) {
        this.idloja = idloja;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + idp +
                ", nome_do_produto='" + nome_do_produto + '\'' +
                ", demanda=" + demanda +
                ", preco=" + preco +
                ", data_vencimento_oferta='" + data_vencimento_oferta + '\'' +
                ", imagem='" + imagem + '\'' +
                ", idloja=" + idloja +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
