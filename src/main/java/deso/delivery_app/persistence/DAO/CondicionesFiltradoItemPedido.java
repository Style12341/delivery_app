package deso.delivery_app.persistence.DAO;

public class CondicionesFiltradoItemPedido {
    private boolean filtraPrecioMinimo = false;
    private boolean filtraPrecioMaximo = false;
    private double precioMinimo;
    private double precioMaximo;
    private boolean filtraComida = false;
    private boolean filtraBebida = false;
    private boolean filtraVegano = false;
    private boolean filtraCeliaco = false;
    private boolean filtraNoEsAlcoholico = false;
    private boolean filtraGaseosa;
    private boolean filtraRestaurante = false;
    private long idVendedor;

    public void setRangoPrecio(double precioMinimo, double precioMaximo) {
        this.filtraPrecioMinimo = true;
        this.filtraPrecioMaximo = true;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
    }

    public void setPrecioMinimo(double precioMinimo) {
        this.filtraPrecioMinimo = true;
        this.precioMinimo = precioMinimo;
    }

    public void setPrecioMaximo(double precioMaximo) {
        this.filtraPrecioMaximo = true;
        this.precioMaximo = precioMaximo;
    }

    public void setComidaVegana() {
        this.filtraVegano = true;
    }

    public void setComidaCeliaca() {
        this.filtraCeliaco = true;
    }

    public void setBebidasNoAlcoholica() {
        this.filtraBebida = true;
        this.filtraNoEsAlcoholico = true;
    }

    public void setEsGaseosa() {
        this.filtraBebida = true;
        this.filtraGaseosa = true;
    }

    public void setRestaurante(long id) {
        this.filtraRestaurante = true;
        this.idVendedor = id;
    }

    public void setComida() {
        this.filtraComida = true;
    }

    public boolean esFiltraPrecioMinimo() {
        return filtraPrecioMinimo;
    }

    public boolean esFiltraPrecioMaximo() {
        return filtraPrecioMaximo;
    }


    public boolean esFiltraComida() {
        return filtraComida;
    }

    public boolean esFiltraBebida() {
        return filtraBebida;
    }

    public boolean esFiltraVegano() {
        return filtraVegano;
    }

    public boolean esFiltraCeliaco() {
        return filtraCeliaco;
    }

    public boolean esFiltraNoEsAlcoholico() {
        return filtraNoEsAlcoholico;
    }

    public boolean esFiltraRestaurante() {
        return filtraRestaurante;
    }

    public boolean esFiltraGaseosa() {
        return filtraGaseosa;
    }

    public long getIdVendedor() {
        return idVendedor;
    }

    public double getPrecioMaximo() {
        return precioMaximo;
    }

    public double getPrecioMinimo() {
        return precioMinimo;
    }
}
