package iostreams.task2.model;

import java.io.Serializable;

// АБСТРАКЦІЯ: абстрактний клас (базовий для всіх фігур)
// Serializable — маркерний інтерфейс: дозволяє серіалізацію за замовчуванням
public abstract class Shape implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;

    // ІНКАПСУЛЯЦІЯ: поле приховане (private)
    private String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    // Абстрактний метод обчислення площі (реалізують нащадки)
    public abstract double calcArea();

    @Override
    public String toString() {
        return "Колiр: " + shapeColor;
    }
}
