package iostreams.task4;

import iostreams.common.ConsoleIO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/** Завдання 5.4: частота тегів на сторінці за URL. */
public class TagFrequencyApp {
    private static final String DEFAULT_URL = "https://kpi.ua";

    public static void main(String[] args) {
        HtmlLoader loader = new HtmlLoader();
        TagCounter counter = new TagCounter();

        while (true) {
            String url = ConsoleIO.readLine("\nВведiть URL сторiнки (Enter - " + DEFAULT_URL + ", 0 - вихiд): ");
            if (url.equals("0")) {
                break;
            }
            if (url.isEmpty()) {
                url = DEFAULT_URL;
            }

            try {
                String html = loader.load(url);
                Map<String, Integer> frequency = counter.count(html);
                if (frequency.isEmpty()) {
                    ConsoleIO.println("На сторiнцi не знайдено жодного тегу.");
                    continue;
                }
                ConsoleIO.println("Завантажено " + html.length() + " символiв, знайдено "
                        + frequency.size() + " рiзних тегiв.");
                printTable("а) Теги в лексикографiчному порядку:", counter.sortByTag(frequency));
                printTable("б) Теги за зростанням частоти:", counter.sortByFrequency(frequency));
            } catch (IllegalArgumentException e) {
                ConsoleIO.println("Некоректний URL: " + e.getMessage());
            } catch (UnknownHostException e) {
                ConsoleIO.println("Не вдалося знайти сервер: " + e.getMessage());
            } catch (IOException e) {
                ConsoleIO.println("Не вдалося завантажити сторiнку: " + e.getMessage());
            }
        }
        ConsoleIO.println("Роботу завершено.");
    }

    private static void printTable(String title, List<Map.Entry<String, Integer>> rows) {
        ConsoleIO.println("\n" + title);
        for (Map.Entry<String, Integer> row : rows) {
            ConsoleIO.println(String.format("  %-12s %5d", row.getKey(), row.getValue()));
        }
    }
}
