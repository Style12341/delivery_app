package deso.delivery_app;

import deso.delivery_app.strategies.PagarStrategy;

import java.util.Date;

public class Pago {
    private long id;
    private Date fecha;
    private PagarStrategy pagarStrategy;
    private double precioTotalSinRecargo;
    private double precioTotalConRecargo;

    public Pago(double precioTotalSinRecargo) {
        this.precioTotalSinRecargo = precioTotalSinRecargo;
    }

    public void setStrategy(PagarStrategy pagarStrategy) {
        this.pagarStrategy = pagarStrategy;
    }

    public void setPrecioTotalConRecargo(double precioTotalConRecargo) {
        this.precioTotalConRecargo = precioTotalConRecargo;
    }
    
    public void pagar(Pedido pedido) {
        try {
            precioTotalConRecargo = pagarStrategy.pagar(pedido);
            this.fecha = new Date();
        } catch (Exception e) {
            System.out.println("Error al pagar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double getPrecioTotalConRecargo() {
        return precioTotalConRecargo;
    }
}
