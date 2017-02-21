package jaj.tct.com.onthebeach.models_geolocalizacao;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class ViewPort extends SugarRecord implements Serializable{

    private Northeast northeast;
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }
}
