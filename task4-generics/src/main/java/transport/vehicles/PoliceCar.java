package transport.vehicles;

import transport.people.Policeman;

/** Поліцейська машина — перевозить тільки поліцейських (обмеження задане типом Policeman). */
public class PoliceCar extends Car<Policeman> {
    public static final int DEFAULT_SEATS = 4;

    public PoliceCar() {
        this(DEFAULT_SEATS);
    }

    public PoliceCar(int maxSeats) {
        super(maxSeats);
    }
}
