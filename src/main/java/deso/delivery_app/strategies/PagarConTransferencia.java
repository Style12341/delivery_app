package deso.delivery_app.strategies;

import deso.delivery_app.Pedido;
import deso.delivery_app.exception.PagoFalladoException;

public class PagarConTransferencia implements PagarStrategy {
    private static final double RECARGO = 1.02;
    private String cbu;
    private String cuit;

    public PagarConTransferencia(String cbu, String cuit) {
        this.cbu = cbu;
        this.cuit = cuit;
    }

    @Override
    public double pagar(Pedido pedido)throws PagoFalladoException {

        double monto = pedido.getPrecioAcumulado();
        double montoAPagar = calcularRecargo(monto);

        // Simulamos que el pago puede tener fallas para manejar el evento
        double random = Math.random();
        if (random<0.1) {
            throw new PagoFalladoException("Pago fallado con mercado pago");
        }

        return montoAPagar;

    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }



    @Override
    public double calcularRecargo(double precioTotalSinRecargo) {
        return precioTotalSinRecargo * RECARGO;
    }
}
