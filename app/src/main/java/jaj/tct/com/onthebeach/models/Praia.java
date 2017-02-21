package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Column;

import java.io.Serializable;

/**
 * Created by jocsa on 15/10/16.
 */
public class Praia extends SugarRecord implements Serializable{

    private long _id;
    private String nome;
    @SerializedName("_id_estado")
    private long estado;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }
}
