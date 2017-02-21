package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jocsa on 13/10/16.
 */
public class Endereco extends SugarRecord implements Serializable{

    @Expose
    private String cep;
    @Expose
    private String rua;
    @Expose
    private String bairro;
    @Expose
    private String estado;
    @Expose
    private String cidade;
    @Expose
    private long numero_residencia;
    @Expose
    private String complemento;
    @Expose
    private String ponto_referencia;
    @Expose
    private long _id_praia;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public long getNumero_residencia() {
        return numero_residencia;
    }

    public void setNumero_residencia(long numero_residencia) {
        this.numero_residencia = numero_residencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPonto_referencia() {
        return ponto_referencia;
    }

    public void setPonto_referencia(String ponto_referencia) {
        this.ponto_referencia = ponto_referencia;
    }

    public long get_id_praia() {
        return _id_praia;
    }

    public void set_id_praia(long _id_praia) {
        this._id_praia = _id_praia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep=" + cep +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", numero_residencia=" + numero_residencia +
                ", complemento='" + complemento + '\'' +
                ", ponto_referencia='" + ponto_referencia + '\'' +
                ", _id_praia=" + _id_praia +
                '}';
    }
}
