package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by jocsa on 13/10/16.
 */
public class ListFormaPagamento extends SugarRecord implements Serializable{

    @Expose
    private boolean dinheiro;
    @Expose
    private boolean outras;
    @Expose
    private Debito debito;
    @Expose
    private Credito credito;
    private String idloja;

    public boolean isDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(boolean dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Debito getDebito() {
        return debito;
    }

    public void setDebito(Debito debito) {
        this.debito = debito;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public boolean isOutras() {
        return outras;
    }

    public void setOutras(boolean outras) {
        this.outras = outras;
    }

    public String getIdloja() {
        return idloja;
    }

    public void setIdloja(String idloja) {
        this.idloja = idloja;
    }
}
