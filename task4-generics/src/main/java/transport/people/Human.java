package transport.people;

/**
 * Людина — базовий клас для всіх, хто може їздити у транспорті.
 * Звичайний пасажир, пожежник і поліцейський є підкласами Human.
 */
public abstract class Human {
    private final String name;

    protected Human(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + name + ")";
    }
}
