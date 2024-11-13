package deso.delivery_app.models;

import deso.delivery_app.TIPO_ITEM;

public class Categoria {
    private long id;
    private String descripcion;
    private TIPO_ITEM tipo_item;
    public Categoria(String descripcion, TIPO_ITEM tipo_item) {

        this.descripcion = descripcion;
        this.tipo_item = tipo_item;
    }

    public TIPO_ITEM getTipoItem() {
        return tipo_item;
    }

    public long getId() {return id;}
    public void setId(long id) {
        this.id = id;
    }
    public String getDescripcion() {return descripcion;}
}
