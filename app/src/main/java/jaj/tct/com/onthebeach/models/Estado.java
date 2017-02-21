package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jocsa on 15/10/16.
 */
public class Estado extends SugarRecord implements Serializable{

    private long _id;
    @SerializedName("estado")
    private String nome;

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
        return nome;
    }
}
