package exception;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(String message) {
        super("Unexpected token: " + message);
    }
}
