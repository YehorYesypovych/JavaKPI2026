package transport.vehicles;

import transport.people.Firefighter;

/** Пожежна машина — перевозить тільки пожежників (обмеження задане типом Firefighter). */
public class FireTruck extends Car<Firefighter> {
    public static final int DEFAULT_SEATS = 6;

    public FireTruck() {
        this(DEFAULT_SEATS);
    }

    public FireTruck(int maxSeats) {
        super(maxSeats);
    }
}
