package iostreams.task2;

import iostreams.task2.model.Circle;
import iostreams.task2.model.Rectangle;
import iostreams.task2.model.Shape;
import iostreams.task2.model.Triangle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.Comparator;

// CONTROLLER (Контролер) — логіка
public class ShapeController {
    private static final String DEFAULT_FILE = "data/shapes.dat";

    private Shape[] shapes;
    private final ShapeView view;
    private final ShapeFileManager fileManager = new ShapeFileManager();

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
            int choice;
            try {
                choice = Integer.parseInt(view.getInput("Оберiть дiю: "));
            } catch (NumberFormatException e) {
                view.printMessage("Некоректне введення: потрiбно ввести число вiд 1 до 8.");
                continue;
            }

            switch (choice) {
                case 1 -> view.printShapes(shapes);
                case 2 -> showTotalArea();
                case 3 -> showAreaByType();
                case 4 -> sortByArea();
                case 5 -> sortByColor();
                case 6 -> saveToFile();
                case 7 -> loadFromFile();
                case 8 -> {
                    return;
                }
                default -> view.printMessage("Невiдома команда.");
            }
        }
    }

    private void showTotalArea() {
        double total = 0;
        // ПОЛІМОРФІЗМ: викликаємо один метод, але кожна фігура рахує по-своєму
        for (Shape s : shapes) {
            total += s.calcArea();
        }
        view.printMessage("Сумарна площа: " + String.format("%.2f", total));
    }

    private void showAreaByType() {
        String type = view.getInput("Введiть тип фiгури (Прямокутник, Трикутник, Коло): ");
        try {
            Class<? extends Shape> shapeClass = parseShapeType(type);
            double total = 0;
            for (Shape s : shapes) {
                if (shapeClass.isInstance(s)) {
                    total += s.calcArea();
                }
            }
            view.printMessage("Площа фiгур типу " + type + ": " + String.format("%.2f", total));
        } catch (IllegalArgumentException e) {
            view.printMessage("Помилка: " + e.getMessage());
        }
    }

    private Class<? extends Shape> parseShapeType(String type) {
        if (type.equalsIgnoreCase("Прямокутник")) {
            return Rectangle.class;
        } else if (type.equalsIgnoreCase("Трикутник")) {
            return Triangle.class;
        } else if (type.equalsIgnoreCase("Коло")) {
            return Circle.class;
        }
        throw new IllegalArgumentException("невiдомий тип фiгури \"" + type + "\"");
    }

    private void sortByArea() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
        view.printMessage("Вiдсортовано за площею!");
        view.printShapes(shapes);
    }

    private void sortByColor() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareTo(s2.getShapeColor());
            }
        });
        view.printMessage("Вiдсортовано за кольором!");
        view.printShapes(shapes);
    }

    /** Запитує шлях до файлу; порожнє введення означає файл за замовчуванням. */
    private String askPath() {
        String path = view.getInput("Введiть шлях i iм'я файлу (Enter - " + DEFAULT_FILE + "): ");
        return path.isEmpty() ? DEFAULT_FILE : path;
    }

    private void saveToFile() {
        String path = askPath();
        try {
            fileManager.save(shapes, path);
            view.printMessage("Набiр з " + shapes.length + " фiгур збережено у файл " + path);
        } catch (IOException e) {
            view.printMessage("Помилка збереження: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        String path = askPath();
        try {
            shapes = fileManager.load(path);
            view.printMessage("З файлу " + path + " завантажено " + shapes.length + " фiгур:");
            view.printShapes(shapes);
        } catch (FileNotFoundException e) {
            view.printMessage("Помилка: файл не знайдено - " + path);
        } catch (StreamCorruptedException e) {
            view.printMessage("Помилка: файл " + path + " не мiстить збережених об'єктiв");
        } catch (IOException | ClassNotFoundException e) {
            view.printMessage("Помилка читання файлу: " + e.getMessage());
        }
    }
}
