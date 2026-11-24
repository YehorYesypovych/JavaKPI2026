package lambda.task1;

import java.util.function.IntPredicate;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Пошук досконалих чисел за допомогою лямбда-виразiв i Stream API.
 * Досконале число дорiвнює сумi всiх своїх дiльникiв, крiм самого себе (6 = 1 + 2 + 3).
 */
public final class PerfectNumbers {

    /**
     * Сума власних дiльникiв числа n. Дiльники шукаються лише до √n:
     * кожен знайдений дiльник d дає пару d i n / d (для d = √n — одне число).
     */
    public static final IntToLongFunction SUM_OF_DIVISORS = n -> n == 1 ? 0 :
            1 + IntStream.rangeClosed(2, (int) Math.sqrt(n))
                    .filter(d -> n % d == 0)
                    .mapToLong(d -> d == n / d ? d : d + n / d)
                    .sum();

    /** Число досконале, якщо сума його власних дiльникiв дорiвнює самому числу. */
    public static final IntPredicate IS_PERFECT = n -> SUM_OF_DIVISORS.applyAsLong(n) == n;

    private PerfectNumbers() {
    }

    /** Повертає масив усiх досконалих чисел вiд 1 до limit у порядку зростання. */
    public static int[] findPerfect(int limit) {
        return IntStream.rangeClosed(1, limit)
                .parallel()                 // числа перевiряються паралельно на всiх ядрах
                .filter(IS_PERFECT)
                .toArray();                 // toArray() зберiгає порядок зростання
    }

    /** Розкладає число на суму власних дiльникiв, наприклад «28 = 1 + 2 + 4 + 7 + 14». */
    public static String divisorsAsSum(int n) {
        return IntStream.range(1, n)
                .filter(d -> n % d == 0)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" + ", n + " = ", ""));
    }
}
