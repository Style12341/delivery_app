package deso.delivery_app.models;

import deso.delivery_app.strategies.PagarConMercadoPago;
import deso.delivery_app.strategies.PagarConTransferencia;
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
    public void setId(long id) {
        this.id = id;
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

    public Date getFecha() {
        return fecha;
    }

    public double getPrecioTotalSinRecargo() {
        return precioTotalSinRecargo;
    }

    public long getId() { return id; }

    public String getStrategyStr() {
        if(pagarStrategy instanceof PagarConTransferencia) return "Transferencia";
        if(pagarStrategy instanceof PagarConMercadoPago) return "MercadoPago";
        return "";
    }
}
