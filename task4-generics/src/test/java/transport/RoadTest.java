package transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import transport.people.Firefighter;
import transport.people.Passenger;
import transport.people.Policeman;
import transport.vehicles.Bus;
import transport.vehicles.FireTruck;
import transport.vehicles.PoliceCar;
import transport.vehicles.Taxi;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    private Road road;

    @BeforeEach
    void setUp() {
        road = new Road();
    }

    @Test
    @DisplayName("Порожня дорога — 0 людей")
    void emptyRoadHasNoHumans() {
        assertEquals(0, road.getCountOfHumans());
    }

    @Test
    @DisplayName("Пiдрахунок людей у рiзних типах транспорту на дорозi")
    void countsHumansInAllVehicles() {
        Bus bus = new Bus();
        bus.boardPassenger(new Passenger("iван"));
        bus.boardPassenger(new Firefighter("Петро"));
        bus.boardPassenger(new Policeman("Андрiй"));

        Taxi taxi = new Taxi();
        taxi.boardPassenger(new Passenger("Олена"));

        FireTruck fireTruck = new FireTruck();
        fireTruck.boardPassenger(new Firefighter("Василь"));
        fireTruck.boardPassenger(new Firefighter("Микола"));

        PoliceCar policeCar = new PoliceCar();
        policeCar.boardPassenger(new Policeman("Сергiй"));

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(policeCar);

        assertEquals(4, road.carsInRoad.size());
        assertEquals(7, road.getCountOfHumans());
    }

    @Test
    @DisplayName("Порожнiй транспорт не додає людей до пiдрахунку")
    void emptyVehiclesCountAsZero() {
        road.addCarToRoad(new Bus());
        road.addCarToRoad(new FireTruck());
        assertEquals(0, road.getCountOfHumans());
    }

    @Test
    @DisplayName("Пiдрахунок оновлюється пiсля висадки пасажира")
    void countUpdatesAfterDropOff() {
        Taxi taxi = new Taxi();
        Passenger ivan = new Passenger("iван");
        taxi.boardPassenger(ivan);
        taxi.boardPassenger(new Passenger("Олена"));
        road.addCarToRoad(taxi);

        assertEquals(2, road.getCountOfHumans());
        taxi.dropOffPassenger(ivan);
        assertEquals(1, road.getCountOfHumans());
    }

    @Test
    @DisplayName("addAllCarsToRoad приймає List<FireTruck> (wildcard)")
    void addAllAcceptsListOfSubtype() {
        FireTruck t1 = new FireTruck();
        t1.boardPassenger(new Firefighter("А"));
        FireTruck t2 = new FireTruck();
        t2.boardPassenger(new Firefighter("Б"));
        t2.boardPassenger(new Firefighter("В"));

        List<FireTruck> trucks = List.of(t1, t2);
        road.addAllCarsToRoad(trucks);

        assertEquals(2, road.carsInRoad.size());
        assertEquals(3, road.getCountOfHumans());
    }

    @Test
    @DisplayName("Транспорт, прибраний з дороги, не враховується")
    void removedVehicleNotCounted() {
        Bus bus = new Bus();
        bus.boardPassenger(new Passenger("iван"));
        PoliceCar car = new PoliceCar();
        car.boardPassenger(new Policeman("Сергiй"));
        road.addCarToRoad(bus);
        road.addCarToRoad(car);

        assertTrue(road.removeCarFromRoad(bus));
        assertEquals(1, road.getCountOfHumans());
    }

    @Test
    @DisplayName("Не можна додати null на дорогу")
    void nullCarRejected() {
        assertThrows(IllegalArgumentException.class, () -> road.addCarToRoad(null));
    }
}
