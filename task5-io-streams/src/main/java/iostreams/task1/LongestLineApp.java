package iostreams.task1;

import iostreams.common.ConsoleIO;

import java.io.FileNotFoundException;
import java.io.IOException;

/** Завдання 5.1: рядок з максимальною кількістю слів із заданого файлу. */
public class LongestLineApp {
    private static final String DEFAULT_FILE = "data/text.txt";

    public static void main(String[] args) {
        LongestLineFinder finder = new LongestLineFinder();

        while (true) {
            String path = ConsoleIO.readLine(
                    "\nВведiть шлях до файлу (Enter - " + DEFAULT_FILE + ", 0 - вихiд): ");
            if (path.equals("0")) {
                break;
            }
            if (path.isEmpty()) {
                path = DEFAULT_FILE;
            }

            try {
                LineInfo result = finder.findLineWithMaxWords(path);
                if (result == null) {
                    ConsoleIO.println("Файл порожнiй.");
                } else {
                    ConsoleIO.println("Рядок №" + result.number() + " мiстить найбiльше слiв: "
                            + result.wordCount());
                    ConsoleIO.println("Текст рядка: " + result.text());
                }
            } catch (FileNotFoundException e) {
                ConsoleIO.println("Помилка: " + e.getMessage());
            } catch (IOException e) {
                ConsoleIO.println("Помилка читання файлу: " + e.getMessage());
            }
        }
        ConsoleIO.println("Роботу завершено.");
    }
}
