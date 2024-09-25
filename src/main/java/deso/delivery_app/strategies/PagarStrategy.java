package deso.delivery_app.strategies;

import deso.delivery_app.Pedido;
import deso.delivery_app.exception.PagoFalladoException;

public interface PagarStrategy {
    double pagar(Pedido pedido) throws PagoFalladoException;
    double calcularRecargo(double precioTotalSinRecargo);
}
