package deso.delivery_app;

public abstract class ItemMenu {
    protected long id;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected Categoria categoria;
    protected static long NEXTID = 0;
    protected Vendedor vendedor;

    public abstract double peso();

    public abstract boolean esComida();

    public abstract boolean esBebida();

    public abstract boolean aptoVegano();

    public abstract boolean aptoCeliaco();

    public ItemMenu(String nombre, String descripcion, double precio, Categoria categoria) {
        this.id = NEXTID++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public long getId() { return id; }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    public Vendedor getVendedor(){
        return this.vendedor;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
