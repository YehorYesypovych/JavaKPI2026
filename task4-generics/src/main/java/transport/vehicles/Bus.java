package transport.vehicles;

import transport.people.Human;

/** Автобус — перевозить будь-яких пасажирів. */
public class Bus extends Vehicle<Human> {
    public static final int DEFAULT_SEATS = 30;

    public Bus() {
        this(DEFAULT_SEATS);
    }

    public Bus(int maxSeats) {
        super(maxSeats);
    }
}
