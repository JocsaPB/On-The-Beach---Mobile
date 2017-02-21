package jaj.tct.com.onthebeach.models;

import java.util.List;

/**
 * Created by Jocsa on 14/11/2016.
 */

public class FiltroSegmentosECategoria {

    private List<Segmento> segmentos;
    private List<Categoria> categorias;

    public List<Segmento> getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List<Segmento> segmentos) {
        this.segmentos = segmentos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
