package jaj.tct.com.onthebeach.models_geolocalizacao;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class Southwest extends SugarRecord implements Serializable {

    private Long lat;
    private Long lng;

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

}
