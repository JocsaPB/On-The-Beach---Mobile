package jaj.tct.com.onthebeach.models_geolocalizacao;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class Geometry {

    private Location location;
    private String location_type;
    private ViewPort viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public ViewPort getViewport() {
        return viewport;
    }

    public void setViewport(ViewPort viewport) {
        this.viewport = viewport;
    }
}
