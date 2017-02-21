package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jocsa on 25/11/2016.
 */

public class Telefones extends SugarRecord implements Serializable{


    @Expose
    @SerializedName("0")
    private String um;
    @Expose
    @SerializedName("1")
    private String dois;
    @Expose
    @SerializedName("2")
    private String tres;
    private String idloja;

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public String getDois() {
        return dois;
    }

    public void setDois(String dois) {
        this.dois = dois;
    }

    public String getTres() {
        return tres;
    }

    public void setTres(String tres) {
        this.tres = tres;
    }

    public String getIdloja() {
        return idloja;
    }

    public void setIdloja(String idloja) {
        this.idloja = idloja;
    }
}
