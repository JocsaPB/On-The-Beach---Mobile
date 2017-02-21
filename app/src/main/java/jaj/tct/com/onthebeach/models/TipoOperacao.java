package jaj.tct.com.onthebeach.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jocsa on 11/11/2016.
 */
public class TipoOperacao extends SugarRecord implements Serializable{

    private String operacao;

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
