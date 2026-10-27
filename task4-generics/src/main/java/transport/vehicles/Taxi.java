package transport.vehicles;

import transport.people.Human;

/** Таксі — перевозить будь-яких пасажирів. */
public class Taxi extends Car<Human> {
    public static final int DEFAULT_SEATS = 4;

    public Taxi() {
        this(DEFAULT_SEATS);
    }

    public Taxi(int maxSeats) {
        super(maxSeats);
    }
}
