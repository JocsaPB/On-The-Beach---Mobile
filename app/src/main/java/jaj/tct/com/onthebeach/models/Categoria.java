package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by Jocsa on 11/11/2016.
 */

public class Categoria extends SugarRecord{

    private long _id;
    @SerializedName("_id_segmento")
    private long segmento;
    private String categoria;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getSegmento() {
        return segmento;
    }

    public void setSegmento(long segmento) {
        this.segmento = segmento;
    }

    public long get_id_segmento() {
        return segmento;
    }

    public void set_id_segmento(long _id_segmento) {
        this.segmento = _id_segmento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
