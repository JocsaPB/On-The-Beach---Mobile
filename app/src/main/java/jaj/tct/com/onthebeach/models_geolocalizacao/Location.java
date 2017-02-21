package jaj.tct.com.onthebeach.models_geolocalizacao;

import java.io.Serializable;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class Location implements Serializable{

    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
