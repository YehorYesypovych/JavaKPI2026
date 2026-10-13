import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

interface Drawable {
    void draw();
}

// АБСТРАКЦiЯ: Абстрактний клас (базовий для всiх фiгур)
abstract class Shape implements Drawable {
    // iНКАПСУЛЯЦiЯ: поле приховане (private)
    private String shapeColor; 

    // Конструктор
    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    // Метод для отримання кольору
    public String getShapeColor() {
        return shapeColor;
    }

    // Абстрактний метод обчислення площi (реалiзують нащадки)
    public abstract double calcArea();

    // Перевизначений метод Object
    @Override
    public String toString() {
        return "Колiр: " + shapeColor;
    }
}

// СПАДКУВАННЯ: Прямокутник наслiдує Shape
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Малюю прямокутник");
    }

    @Override
    public String toString() {
        return "Прямокутник | " + super.toString() + " | Площа: " + calcArea();
    }
}

// СПАДКУВАННЯ: Трикутник наслiдує Shape
class Triangle extends Shape {
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

// СПАДКУВАННЯ: Коло наслiдує Shape
class Circle extends Shape {
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

// ==========================================
// VIEW (Представлення) - Виведення i меню
// ==========================================
class ShapeView {
    private Scanner scanner;

    public ShapeView() throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "cp866"));
        this.scanner = new Scanner(System.in, "cp866");
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void printShapes(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i + 1) + ". " + shapes[i].toString());
        }
    }

    public void printMenu() {
        System.out.println("\n=== МЕНЮ ФiГУР ===");
        System.out.println("1. Вiдобразити всi фiгури");
        System.out.println("2. Сумарна площа всiх фiгур");
        System.out.println("3. Сумарна площа фiгур заданого виду");
        System.out.println("4. Сортувати за збiльшенням площi");
        System.out.println("5. Сортувати за кольором");
        System.out.println("6. Вихiд");
        System.out.print("Оберiть дiю: ");
    }

    public String getInput() {
        return scanner.nextLine();
    }
}

// ==========================================
// CONTROLLER (Контролер) - Логiка
// ==========================================
class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController(ShapeView view) {
        this.view = view;
        initShapes();
    }

    private void initShapes() {
        shapes = new Shape[10]; 
        shapes[0] = new Rectangle("Червоний", 5, 10);
        shapes[1] = new Circle("Синiй", 7);
        shapes[2] = new Triangle("Зелений", 4, 6);
        shapes[3] = new Rectangle("Жовтий", 3, 8);
        shapes[4] = new Circle("Червоний", 3.5);
        shapes[5] = new Triangle("Синiй", 5, 5);
        shapes[6] = new Rectangle("Зелений", 6, 6);
        shapes[7] = new Circle("Жовтий", 10);
        shapes[8] = new Triangle("Червоний", 8, 4);
        shapes[9] = new Rectangle("Синiй", 2, 4);
    }

    public void run() {
        view.printMessage("Початковий масив фiгур:");
        view.printShapes(shapes);

        while (true) {
            view.printMenu();
            String choice = view.getInput();

            if (choice.equals("1")) {
                view.printShapes(shapes);
            } else if (choice.equals("2")) {
                double total = 0;
                // ПОЛiМОРФiЗМ: Викликаємо один метод, але кожен рахує по-своєму
                for (Shape s : shapes) {
                    total += s.calcArea(); 
                }
                view.printMessage("Сумарна площа: " + String.format("%.2f", total));
            } else if (choice.equals("3")) {
                view.printMessage("Введiть тип фiгури (Прямокутник, Трикутник, Коло):");
                String type = view.getInput();
                double total = 0;
                
                for (Shape s : shapes) {
                    // Перевiряємо клас об'єкта
                    if (type.equalsIgnoreCase("Прямокутник") && s instanceof Rectangle) {
                        total += s.calcArea();
                    } else if (type.equalsIgnoreCase("Трикутник") && s instanceof Triangle) {
                        total += s.calcArea();
                    } else if (type.equalsIgnoreCase("Коло") && s instanceof Circle) {
                        total += s.calcArea();
                    }
                }
                view.printMessage("Площа фiгур типу " + type + ": " + String.format("%.2f", total));
            } else if (choice.equals("4")) {
                // Сортування за площею через Comparator
                Arrays.sort(shapes, new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        return Double.compare(s1.calcArea(), s2.calcArea());
                    }
                });
                view.printMessage("Вiдсортовано за площею!");
                view.printShapes(shapes);
            } else if (choice.equals("5")) {
                // Сортування за кольором через Comparator
                Arrays.sort(shapes, new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        return s1.getShapeColor().compareTo(s2.getShapeColor());
                    }
                });
                view.printMessage("Вiдсортовано за кольором!");
                view.printShapes(shapes);
            } else if (choice.equals("6")) {
                break;
            } else {
                view.printMessage("Невiдома команда.");
            }
        }
    }
}

// ==========================================
// ГОЛОВНИЙ КЛАС
// ==========================================
public class Main {
    public static void main(String[] args) {
        try {
            ShapeView view = new ShapeView();
            ShapeController controller = new ShapeController(view);
            controller.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}