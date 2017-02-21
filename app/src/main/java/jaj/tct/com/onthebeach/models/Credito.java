package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

/**
 * Created by Jocsa on 25/11/2016.
 */
public class Credito extends SugarRecord implements Serializable{

    @Expose
    private boolean visa;
    @Expose
    private boolean master;
    @Expose
    private boolean hiper;
    @Expose
    private boolean elo;

    private Long idformapagamento;

    public boolean isVisa() {
        return visa;
    }

    public void setVisa(boolean visa) {
        this.visa = visa;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public boolean isHiper() {
        return hiper;
    }

    public void setHiper(boolean hiper) {
        this.hiper = hiper;
    }

    public boolean isElo() {
        return elo;
    }

    public void setElo(boolean elo) {
        this.elo = elo;
    }

    public Long getIdformapagamento() {
        return idformapagamento;
    }

    public void setIdformapagamento(Long idformapagamento) {
        this.idformapagamento = idformapagamento;
    }
}
