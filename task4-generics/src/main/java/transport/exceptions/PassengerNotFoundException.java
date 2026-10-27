package transport.exceptions;

/** Кидається, коли пасажира, якого треба висадити, немає у транспортному засобі. */
public class PassengerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PassengerNotFoundException(String message) {
        super(message);
    }
}
