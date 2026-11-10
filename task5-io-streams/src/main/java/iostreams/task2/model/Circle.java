package iostreams.task2.model;

public class Circle extends Shape {
    private static final long serialVersionUID = 1L;

    private double radius;

    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Малюю коло");
    }

    @Override
    public String toString() {
        return "Коло | " + super.toString() + " | Площа: " + String.format("%.2f", calcArea());
    }
}
