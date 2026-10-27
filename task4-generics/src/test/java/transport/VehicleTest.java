package transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import transport.exceptions.NoFreeSeatsException;
import transport.exceptions.PassengerAlreadyOnBoardException;
import transport.exceptions.PassengerNotFoundException;
import transport.people.Firefighter;
import transport.people.Human;
import transport.people.Passenger;
import transport.people.Policeman;
import transport.vehicles.Bus;
import transport.vehicles.FireTruck;
import transport.vehicles.PoliceCar;
import transport.vehicles.Taxi;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    private Passenger passenger;
    private Firefighter firefighter;
    private Policeman policeman;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("iван");
        firefighter = new Firefighter("Петро");
        policeman = new Policeman("Андрiй");
    }

    // ---------- Автобус ----------

    @Test
    @DisplayName("Автобус перевозить пасажирiв усiх типiв")
    void busAcceptsAllTypesOfHumans() {
        Bus bus = new Bus(5);
        bus.boardPassenger(passenger);
        bus.boardPassenger(firefighter);
        bus.boardPassenger(policeman);

        assertEquals(3, bus.getOccupiedSeats());
        assertTrue(bus.contains(passenger));
        assertTrue(bus.contains(firefighter));
        assertTrue(bus.contains(policeman));
    }

    @Test
    @DisplayName("Автобус: максимальна кiлькiсть мiсць за замовчуванням")
    void busDefaultSeats() {
        Bus bus = new Bus();
        assertEquals(Bus.DEFAULT_SEATS, bus.getMaxSeats());
        assertEquals(0, bus.getOccupiedSeats());
    }

    @Test
    @DisplayName("Автобус: посадка понад лiмiт кидає NoFreeSeatsException")
    void busThrowsWhenFull() {
        Bus bus = new Bus(2);
        bus.boardPassenger(passenger);
        bus.boardPassenger(firefighter);

        assertThrows(NoFreeSeatsException.class, () -> bus.boardPassenger(policeman));
        assertEquals(2, bus.getOccupiedSeats());
    }

    // ---------- Таксi ----------

    @Test
    @DisplayName("Таксi перевозить пасажирiв усiх типiв")
    void taxiAcceptsAllTypesOfHumans() {
        Taxi taxi = new Taxi();
        taxi.boardPassenger(passenger);
        taxi.boardPassenger(firefighter);
        taxi.boardPassenger(policeman);

        assertEquals(3, taxi.getOccupiedSeats());
        assertEquals(4, taxi.getMaxSeats());
        assertEquals(1, taxi.getFreeSeats());
    }

    @Test
    @DisplayName("Таксi: п'ятий пасажир не вмiщується")
    void taxiThrowsWhenFull() {
        Taxi taxi = new Taxi();
        for (int i = 0; i < 4; i++) {
            taxi.boardPassenger(new Passenger("Пасажир " + i));
        }
        assertThrows(NoFreeSeatsException.class, () -> taxi.boardPassenger(passenger));
    }

    // ---------- Пожежна машина ----------

    @Test
    @DisplayName("Пожежна машина перевозить пожежникiв")
    void fireTruckAcceptsFirefighters() {
        FireTruck truck = new FireTruck(3);
        truck.boardPassenger(firefighter);
        truck.boardPassenger(new Firefighter("Василь"));

        assertEquals(2, truck.getOccupiedSeats());
        assertEquals(3, truck.getMaxSeats());
        // truck.boardPassenger(passenger); — не компiлюється: Passenger не є Firefighter
        // truck.boardPassenger(policeman); — не компiлюється: Policeman не є Firefighter
    }

    @Test
    @DisplayName("Пожежна машина: посадка понад лiмiт кидає виняток")
    void fireTruckThrowsWhenFull() {
        FireTruck truck = new FireTruck(1);
        truck.boardPassenger(firefighter);
        assertThrows(NoFreeSeatsException.class, () -> truck.boardPassenger(new Firefighter("Василь")));
    }

    // ---------- Полiцейська машина ----------

    @Test
    @DisplayName("Полiцейська машина перевозить полiцейських")
    void policeCarAcceptsPolicemen() {
        PoliceCar car = new PoliceCar();
        car.boardPassenger(policeman);
        car.boardPassenger(new Policeman("Сергiй"));

        assertEquals(2, car.getOccupiedSeats());
        assertEquals(PoliceCar.DEFAULT_SEATS, car.getMaxSeats());
        // car.boardPassenger(firefighter); — не компiлюється: Firefighter не є Policeman
    }

    @Test
    @DisplayName("Полiцейська машина: посадка понад лiмiт кидає виняток")
    void policeCarThrowsWhenFull() {
        PoliceCar car = new PoliceCar(1);
        car.boardPassenger(policeman);
        assertThrows(NoFreeSeatsException.class, () -> car.boardPassenger(new Policeman("Сергiй")));
    }

    // ---------- Висадка ----------

    @Test
    @DisplayName("Висадка пасажира звiльняє мiсце")
    void dropOffFreesSeat() {
        Bus bus = new Bus(2);
        bus.boardPassenger(passenger);
        bus.boardPassenger(firefighter);

        bus.dropOffPassenger(passenger);

        assertEquals(1, bus.getOccupiedSeats());
        assertFalse(bus.contains(passenger));
        assertTrue(bus.contains(firefighter));
        assertDoesNotThrow(() -> bus.boardPassenger(policeman));
    }

    @Test
    @DisplayName("Висадка вiдсутнього пасажира кидає PassengerNotFoundException")
    void dropOffMissingPassengerThrows() {
        Taxi taxi = new Taxi();
        taxi.boardPassenger(passenger);

        assertThrows(PassengerNotFoundException.class, () -> taxi.dropOffPassenger(policeman));
        assertEquals(1, taxi.getOccupiedSeats());
    }

    @Test
    @DisplayName("Повторна висадка того самого пасажира кидає виняток")
    void doubleDropOffThrows() {
        FireTruck truck = new FireTruck();
        truck.boardPassenger(firefighter);
        truck.dropOffPassenger(firefighter);

        assertThrows(PassengerNotFoundException.class, () -> truck.dropOffPassenger(firefighter));
    }

    @Test
    @DisplayName("Висадка з порожньої полiцейської машини кидає виняток")
    void dropOffFromEmptyVehicleThrows() {
        PoliceCar car = new PoliceCar();
        assertThrows(PassengerNotFoundException.class, () -> car.dropOffPassenger(policeman));
    }

    // ---------- iншi перевiрки ----------

    @Test
    @DisplayName("Той самий пасажир не може сiсти двiчi")
    void samePassengerCannotBoardTwice() {
        Bus bus = new Bus();
        bus.boardPassenger(passenger);
        assertThrows(PassengerAlreadyOnBoardException.class, () -> bus.boardPassenger(passenger));
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    @DisplayName("Не можна посадити null")
    void nullPassengerRejected() {
        Bus bus = new Bus();
        assertThrows(IllegalArgumentException.class, () -> bus.boardPassenger(null));
    }

    @Test
    @DisplayName("Транспорт з нульовою або вiд'ємною кiлькiстю мiсць створити не можна")
    void invalidSeatCountRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Bus(0));
        assertThrows(IllegalArgumentException.class, () -> new Taxi(-1));
    }

    @Test
    @DisplayName("Список пасажирiв не можна змiнити ззовнi")
    void passengersListIsReadOnly() {
        Bus bus = new Bus();
        bus.boardPassenger(passenger);
        List<Human> list = bus.getPassengers();
        assertThrows(UnsupportedOperationException.class, () -> list.add(policeman));
    }

    // ---------- boardAll з wildcard ----------

    @Test
    @DisplayName("boardAll: у автобус можна посадити List<Firefighter> (wildcard ? extends T)")
    void boardAllAcceptsSubtypeList() {
        Bus bus = new Bus(5);
        List<Firefighter> crew = List.of(new Firefighter("А"), new Firefighter("Б"), new Firefighter("В"));

        bus.boardAll(crew);

        assertEquals(3, bus.getOccupiedSeats());
    }

    @Test
    @DisplayName("boardAll: якщо мiсць не вистачає на всю групу, не сiдає нiхто")
    void boardAllIsAllOrNothing() {
        FireTruck truck = new FireTruck(2);
        List<Firefighter> crew = List.of(new Firefighter("А"), new Firefighter("Б"), new Firefighter("В"));

        assertThrows(NoFreeSeatsException.class, () -> truck.boardAll(crew));
        assertEquals(0, truck.getOccupiedSeats());
    }
}
