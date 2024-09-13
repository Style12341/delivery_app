package deso.delivery_app;

public class Pedido {
    protected long id;
    protected long id_vendedor;
    protected static long NEXT_ID = 0;

    public Pedido(long id_vendedor) {
        this.id = NEXT_ID++;
        this.id_vendedor = id_vendedor;
    }

    public long getIDVendedor() {
        return id_vendedor;
    }
}
