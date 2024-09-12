package deso.delivery_app;

public class ItemPedido {
    private long id;
    private int cantidad;
    private double precioTotal;
    private ItemMenu itemMenu;
    private Pedido pedido;
    private static long NEXTID = 0;

    public ItemPedido(int cantidad, ItemMenu itemMenu, Pedido pedido) {
        this.id = NEXTID++;
        this.cantidad = cantidad;
        this.itemMenu = itemMenu;
        this.pedido = pedido;
        this.precioTotal = itemMenu.getPrecio() * cantidad;
    }

    public ItemMenu getItemMenu() {
        return itemMenu;
    }

    public long getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setItemMenu(ItemMenu itemMenu) {
        this.itemMenu = itemMenu;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
