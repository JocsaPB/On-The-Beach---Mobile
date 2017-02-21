package jaj.tct.com.onthebeach.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by jocsa on 13/10/16.
 */
public class Segmento extends SugarRecord{
    /**Model ainda não está sendo utilizado*/

    private long _id;
    private String segmento;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }
}
