package deso.delivery_app.strategies;

import deso.delivery_app.Pedido;
import deso.delivery_app.exception.PagoFalladoException;

public class PagarConMercadoPago implements PagarStrategy {
    private static final double RECARGO = 1.04;
    private String alias;

    public PagarConMercadoPago(String alias) {
        this.alias = alias;
    }

    public double pagar(Pedido pedido) throws PagoFalladoException {

        double monto = pedido.getPrecioAcumulado();

        // Simulamos que el pago puede tener fallas para manejar el evento
        double random = Math.random();
        if (random < 0.1) {
            throw new PagoFalladoException("Pago fallado con mercado pago");
        }

        return monto * RECARGO;

    }

    @Override
    public double calcularRecargo(double precioTotalSinRecargo) {
        return precioTotalSinRecargo * RECARGO;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
