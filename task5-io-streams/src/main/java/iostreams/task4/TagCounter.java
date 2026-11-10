package iostreams.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Підрахунок частоти тегів у HTML-коді сторінки. */
public class TagCounter {
    // HTML-коментарі <!-- ... -->
    private static final Pattern COMMENT = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
    // Вміст <script> і <style> — там можуть бути символи '<', які не є тегами
    private static final Pattern SCRIPT_OR_STYLE = Pattern.compile(
            "(<(script|style)\\b[^>]*>).*?(</\\2\\s*>)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    // Відкриваючий тег: '<' і одразу ім'я тегу (закриваючі </...> та <!DOCTYPE> не підходять)
    private static final Pattern OPEN_TAG = Pattern.compile("<([a-zA-Z][a-zA-Z0-9:-]*)");

    /** Повертає частоти тегів; TreeMap зберігає ключі в лексикографічному порядку. */
    public Map<String, Integer> count(String html) {
        String cleaned = COMMENT.matcher(html).replaceAll("");
        cleaned = SCRIPT_OR_STYLE.matcher(cleaned).replaceAll("$1$3");

        Map<String, Integer> frequency = new TreeMap<>();
        Matcher matcher = OPEN_TAG.matcher(cleaned);
        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase(Locale.ROOT);
            frequency.merge(tag, 1, Integer::sum);
        }
        return frequency;
    }

    /** а) За зростанням: теги в лексикографічному порядку. */
    public List<Map.Entry<String, Integer>> sortByTag(Map<String, Integer> frequency) {
        return new ArrayList<>(new TreeMap<>(frequency).entrySet());
    }

    /** б) За зростанням частоти появи тегів (при однаковій частоті — за назвою). */
    public List<Map.Entry<String, Integer>> sortByFrequency(Map<String, Integer> frequency) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(frequency.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));
        return list;
    }
}
