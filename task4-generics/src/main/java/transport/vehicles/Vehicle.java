package transport.vehicles;

import transport.exceptions.NoFreeSeatsException;
import transport.exceptions.PassengerAlreadyOnBoardException;
import transport.exceptions.PassengerNotFoundException;
import transport.people.Human;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Транспортний засіб.
 *
 * @param <T> тип пасажирів, яких може перевозити цей транспорт.
 *            Обмеження {@code T extends Human} гарантує, що пасажиром може бути тільки людина.
 */
public abstract class Vehicle<T extends Human> {
    private final int maxSeats;
    private final List<T> passengers = new ArrayList<>();

    protected Vehicle(int maxSeats) {
        if (maxSeats <= 0) {
            throw new IllegalArgumentException("Кількість місць має бути додатною: " + maxSeats);
        }
        this.maxSeats = maxSeats;
    }

    /** Максимальна кількість місць. */
    public int getMaxSeats() {
        return maxSeats;
    }

    /** Кількість зайнятих місць. */
    public int getOccupiedSeats() {
        return passengers.size();
    }

    /** Кількість вільних місць. */
    public int getFreeSeats() {
        return maxSeats - passengers.size();
    }

    /**
     * Посадка пасажира.
     *
     * @throws NoFreeSeatsException             якщо всі місця зайняті
     * @throws PassengerAlreadyOnBoardException якщо цей пасажир уже сидить у транспорті
     */
    public void boardPassenger(T passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null");
        }
        if (passengers.contains(passenger)) {
            throw new PassengerAlreadyOnBoardException(
                    passenger + " вже сидить у " + getClass().getSimpleName());
        }
        if (getFreeSeats() == 0) {
            throw new NoFreeSeatsException(
                    "У " + getClass().getSimpleName() + " немає вільних місць (" + maxSeats + "/" + maxSeats + ")");
        }
        passengers.add(passenger);
    }

    /**
     * Посадка групи пасажирів.
     * Wildcard {@code ? extends T}: у Vehicle&lt;Human&gt; можна посадити як List&lt;Human&gt;,
     * так і List&lt;Firefighter&gt; чи List&lt;Policeman&gt;.
     * Якщо місць на всіх не вистачає, не сідає ніхто.
     */
    public void boardAll(Collection<? extends T> group) {
        if (group.size() > getFreeSeats()) {
            throw new NoFreeSeatsException("Недостатньо місць: потрібно " + group.size()
                    + ", вільно " + getFreeSeats());
        }
        for (T passenger : group) {
            boardPassenger(passenger);
        }
    }

    /**
     * Висадка пасажира.
     *
     * @throws PassengerNotFoundException якщо такого пасажира немає у транспорті
     */
    public void dropOffPassenger(T passenger) {
        if (!passengers.remove(passenger)) {
            throw new PassengerNotFoundException(
                    passenger + " не сидить у " + getClass().getSimpleName());
        }
    }

    /** Чи сидить пасажир у транспорті. */
    public boolean contains(Human human) {
        return passengers.contains(human);
    }

    /** Список пасажирів (тільки для читання). */
    public List<T> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getOccupiedSeats() + "/" + maxSeats + "] " + passengers;
    }
}
