package deso.delivery_app.models;

public class ItemPedido {
    private int cantidad;
    private double precioTotal;
    private ItemMenu itemMenu;
    private Pedido pedido;

    public ItemPedido(int cantidad, ItemMenu itemMenu, Pedido pedido) {
        this.cantidad = cantidad;
        this.itemMenu = itemMenu;
        this.pedido = pedido;
        this.precioTotal = itemMenu.getPrecio() * cantidad;
    }

    public ItemMenu getItemMenu() {
        return itemMenu;
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
