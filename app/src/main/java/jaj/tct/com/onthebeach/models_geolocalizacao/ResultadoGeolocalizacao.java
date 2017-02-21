package jaj.tct.com.onthebeach.models_geolocalizacao;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class ResultadoGeolocalizacao extends SugarRecord implements Serializable{

    private Result result;
    private String status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
