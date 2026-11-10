package iostreams.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Клас для роботи з файлом: пошук рядка з найбільшою кількістю слів. */
public class LongestLineFinder {
    // Слово — послідовність літер або цифр; апостроф чи дефіс допускаються всередині слова
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}]+(?:['’ʼ-][\\p{L}\\p{N}]+)*");

    /** Підраховує кількість слів у рядку. */
    public static int countWords(String line) {
        Matcher matcher = WORD.matcher(line);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * Читає файл построково і повертає перший рядок з максимальною кількістю слів.
     * Якщо файл порожній — повертає null.
     */
    public LineInfo findLineWithMaxWords(String path) throws IOException {
        File file = new File(path);
        if (!file.isFile()) {
            throw new FileNotFoundException("Файл не знайдено: " + path);
        }

        LineInfo best = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            int number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                int words = countWords(line);
                if (best == null || words > best.wordCount()) {
                    best = new LineInfo(number, line, words);
                }
            }
        }
        return best;
    }
}
