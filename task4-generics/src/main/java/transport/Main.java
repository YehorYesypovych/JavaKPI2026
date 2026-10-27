package transport;

import transport.exceptions.NoFreeSeatsException;
import transport.exceptions.PassengerNotFoundException;
import transport.people.Firefighter;
import transport.people.Passenger;
import transport.people.Policeman;
import transport.vehicles.Bus;
import transport.vehicles.FireTruck;
import transport.vehicles.PoliceCar;
import transport.vehicles.Taxi;

public class Main {
    public static void main(String[] args) {
        Passenger ivan = new Passenger("iван");
        Passenger olena = new Passenger("Олена");
        Firefighter petro = new Firefighter("Петро");
        Policeman andrii = new Policeman("Андрiй");

        Bus bus = new Bus(3);
        bus.boardPassenger(ivan);
        bus.boardPassenger(petro);
        bus.boardPassenger(andrii);

        Taxi taxi = new Taxi();
        taxi.boardPassenger(olena);

        FireTruck fireTruck = new FireTruck();
        fireTruck.boardPassenger(new Firefighter("Василь"));
        // fireTruck.boardPassenger(ivan);   // помилка компiляцiї: Passenger не є Firefighter

        PoliceCar policeCar = new PoliceCar();
        policeCar.boardPassenger(new Policeman("Сергiй"));
        // policeCar.boardPassenger(petro);  // помилка компiляцiї: Firefighter не є Policeman

        System.out.println(bus);
        System.out.println(taxi);
        System.out.println(fireTruck);
        System.out.println(policeCar);

        try {
            bus.boardPassenger(olena);
        } catch (NoFreeSeatsException e) {
            System.out.println("Виняток: " + e.getMessage());
        }

        try {
            taxi.dropOffPassenger(ivan);
        } catch (PassengerNotFoundException e) {
            System.out.println("Виняток: " + e.getMessage());
        }

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(policeCar);
        System.out.println("Людей на дiлянцi дороги: " + road.getCountOfHumans());
    }
}
