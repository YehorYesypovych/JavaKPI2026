package iostreams.task2.model;

public class Triangle extends Shape {
    private static final long serialVersionUID = 1L;

    private double base;
    private double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Малюю трикутник");
    }

    @Override
    public String toString() {
        return "Трикутник | " + super.toString() + " | Площа: " + calcArea();
    }
}
