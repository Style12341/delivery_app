package deso.delivery_app.exception;

public class PagoInexistenteException extends RuntimeException {
    public PagoInexistenteException(String message) {
        super(message);
    }
}
