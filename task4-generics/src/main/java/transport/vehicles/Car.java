package transport.vehicles;

import transport.people.Human;

/**
 * Автомобіль — проміжний рівень ієрархії між Vehicle і таксі / службовими машинами.
 */
public abstract class Car<T extends Human> extends Vehicle<T> {
    protected Car(int maxSeats) {
        super(maxSeats);
    }
}
