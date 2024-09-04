package deso.delivery_app;

public class Categoria {
    private long id;
    private String descripcion;
    private TIPO_ITEM tipo_item;
    private static long NEXTID = 0;

    public Categoria(String descripcion, TIPO_ITEM tipo_item) {
        this.id = NEXTID++;
        this.descripcion = descripcion;
        this.tipo_item = tipo_item;
    }
}
