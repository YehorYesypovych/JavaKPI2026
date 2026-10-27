package transport;

import transport.people.Human;
import transport.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ділянка дороги, на якій знаходяться транспортні засоби.
 *
 * Wildcard {@code Vehicle<? extends Human>} дозволяє тримати в одному списку
 * Bus (Vehicle&lt;Human&gt;), FireTruck (Vehicle&lt;Firefighter&gt;) і PoliceCar (Vehicle&lt;Policeman&gt;).
 * Без wildcard список List&lt;Vehicle&lt;Human&gt;&gt; не прийняв би FireTruck,
 * бо Vehicle&lt;Firefighter&gt; не є підтипом Vehicle&lt;Human&gt;.
 */
public class Road {
    public List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    /** Кількість людей, які зараз перевозяться транспортом на цій ділянці дороги. */
    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<? extends Human> vehicle : carsInRoad) {
            count += vehicle.getOccupiedSeats();
        }
        return count;
    }

    /** Додати транспортний засіб на дорогу. */
    public void addCarToRoad(Vehicle<? extends Human> car) {
        if (car == null) {
            throw new IllegalArgumentException("Транспортний засіб не може бути null");
        }
        carsInRoad.add(car);
    }

    /** Додати кілька транспортних засобів одразу (наприклад, List&lt;FireTruck&gt;). */
    public void addAllCarsToRoad(Collection<? extends Vehicle<? extends Human>> cars) {
        for (Vehicle<? extends Human> car : cars) {
            addCarToRoad(car);
        }
    }

    /** Прибрати транспортний засіб з дороги. */
    public boolean removeCarFromRoad(Vehicle<? extends Human> car) {
        return carsInRoad.remove(car);
    }
}
