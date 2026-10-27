package transport.exceptions;

/** Кидається, коли пасажир, який уже сидить у транспорті, намагається сісти вдруге. */
public class PassengerAlreadyOnBoardException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PassengerAlreadyOnBoardException(String message) {
        super(message);
    }
}
