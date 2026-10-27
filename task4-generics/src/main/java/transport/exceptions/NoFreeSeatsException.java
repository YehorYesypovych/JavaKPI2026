package transport.exceptions;

/** Кидається, коли у транспортному засобі немає вільних місць. */
public class NoFreeSeatsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoFreeSeatsException(String message) {
        super(message);
    }
}
