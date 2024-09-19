package deso.delivery_app;

public class Pedido {
    protected long id;
    protected Cliente cliente;
    protected Vendedor vendedor;
    protected static long NEXT_ID = 0;

    public Pedido(Vendedor vendedor, Cliente cliente) {
        this.id = NEXT_ID++;
        this.vendedor = vendedor;
        this.cliente = cliente;
    }

    public Vendedor getVendedor() { return vendedor; }

    public Cliente getCliente() { return cliente; }
}
