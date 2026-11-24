package lambda.task1;

import lambda.common.ConsoleIO;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/** Simple Task, варiант 9: пошук досконалих чисел вiд 1 до заданої межi (лямбда-версiя КП №1). */
public class PerfectNumbersApp {
    private static final int MAX_LIMIT = 1_000_000;

    /** Рядок є цiлим числом: необов'язковий мiнус i лише цифри. */
    private static final Predicate<String> IS_INTEGER = s -> s.matches("-?\\d+");

    /** Межа дiапазону має бути вiд 1 до MAX_LIMIT. */
    private static final IntPredicate IN_RANGE = n -> n >= 1 && n <= MAX_LIMIT;

    public static void main(String[] args) {
        ConsoleIO.println("=== ПОШУК ДОСКОНАЛИХ ЧИСЕЛ ===");
        while (true) {
            String input = ConsoleIO.readLine(
                    "Введiть межу дiапазону (1.." + MAX_LIMIT + ", 0 - вихiд): ");
            if (input.equals("0")) {
                ConsoleIO.println("Роботу завершено.");
                return;
            }
            if (!IS_INTEGER.test(input)) {
                ConsoleIO.println("Ви ввели текст або некоректнi символи, а потрiбно цiле число!");
                continue;
            }
            // бiльше 7 цифр — точно поза дiапазоном (i не переповнить int пiд час parseInt)
            if (input.replace("-", "").length() > 7 || !IN_RANGE.test(Integer.parseInt(input))) {
                ConsoleIO.println("Межа дiапазону має бути вiд 1 до " + MAX_LIMIT + "!");
                continue;
            }
            printPerfect(Integer.parseInt(input));
        }
    }

    private static void printPerfect(int limit) {
        int[] perfect = PerfectNumbers.findPerfect(limit);
        if (perfect.length == 0) {
            ConsoleIO.println("У дiапазонi вiд 1 до " + limit + " досконалих чисел немає.");
            return;
        }
        ConsoleIO.println("Досконалi числа вiд 1 до " + limit + ": " + Arrays.toString(perfect));
        Arrays.stream(perfect)
                .mapToObj(PerfectNumbers::divisorsAsSum)
                .forEach(ConsoleIO::println);
        ConsoleIO.println("");
    }
}
