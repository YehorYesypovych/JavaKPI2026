package iostreams.task2;

import iostreams.common.ConsoleIO;
import iostreams.task2.model.Shape;

// VIEW (Представлення) — виведення і меню
public class ShapeView {

    public void printMessage(String msg) {
        ConsoleIO.println(msg);
    }

    public void printShapes(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            ConsoleIO.println((i + 1) + ". " + shapes[i].toString());
        }
    }

    public void printMenu() {
        ConsoleIO.println("\n=== МЕНЮ ФIГУР ===");
        ConsoleIO.println("1. Вiдобразити всi фiгури");
        ConsoleIO.println("2. Сумарна площа всiх фiгур");
        ConsoleIO.println("3. Сумарна площа фiгур заданого виду");
        ConsoleIO.println("4. Сортувати за збiльшенням площi");
        ConsoleIO.println("5. Сортувати за кольором");
        ConsoleIO.println("6. Зберегти набiр фiгур у файл");
        ConsoleIO.println("7. Завантажити набiр фiгур з файлу");
        ConsoleIO.println("8. Вихiд");
    }

    public String getInput(String prompt) {
        return ConsoleIO.readLine(prompt);
    }
}
