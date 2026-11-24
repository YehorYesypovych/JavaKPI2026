package collections.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Перекладач з англiйської мови на українську.
 * Словник зберiгається у колекцiї HashMap: ключ — англiйське слово
 * в нижньому регiстрi, значення — український переклад.
 */
public class Translator {
    /** Англiйське слово: латинськi лiтери, можливо з апострофом усерединi (don't, it's). */
    private static final Pattern WORD = Pattern.compile("[A-Za-z]+(?:'[A-Za-z]+)?");

    /** Артиклi українською не перекладаються, тому пiд час перекладу пропускаються. */
    private static final Set<String> ARTICLES = Set.of("a", "an", "the");

    private final HashMap<String, String> dictionary = new HashMap<>();

    /**
     * Додає пару слiв до словника або оновлює переклад наявного слова.
     * @return true — слово додано вперше, false — переклад замiнено
     */
    public boolean addWord(String english, String ukrainian) {
        if (english == null || ukrainian == null || english.isBlank() || ukrainian.isBlank()) {
            throw new IllegalArgumentException("слово i переклад не можуть бути порожнiми");
        }
        String key = english.trim().toLowerCase();
        if (!WORD.matcher(key).matches()) {
            throw new IllegalArgumentException("недопустимi символи в англiйському словi: " + key);
        }
        return dictionary.put(key, ukrainian.trim()) == null;
    }

    /** Перекладає фразу слово за словом, зберiгаючи пробiли i роздiловi знаки. */
    public String translate(String phrase) {
        Matcher matcher = WORD.matcher(phrase);
        StringBuilder result = new StringBuilder();
        boolean sentenceStart = true;
        int prevEnd = 0;

        while (matcher.find()) {
            if (hasSentenceEnd(phrase.substring(prevEnd, matcher.start()))) {
                sentenceStart = true;
            }
            prevEnd = matcher.end();
            String word = matcher.group();
            String key = word.toLowerCase();

            if (ARTICLES.contains(key)) {
                matcher.appendReplacement(result, "");      // артикль видаляємо
                continue;
            }
            String translation = dictionary.get(key);
            String replacement;
            if (translation == null) {
                replacement = "[" + word + "]";             // слова немає у словнику
            } else if (sentenceStart) {
                replacement = capitalize(translation);
            } else {
                replacement = translation;
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            sentenceStart = false;
        }
        matcher.appendTail(result);

        return result.toString()
                .replaceAll(" {2,}", " ")                   // зайвi пробiли пiсля артиклiв
                .replaceAll(" +([,.!?;:])", "$1")           // пробiл перед роздiловим знаком
                .trim();
    }

    /** Повертає слова фрази, яких немає у словнику (без повторiв, у порядку появи). */
    public List<String> findUnknownWords(String phrase) {
        List<String> unknown = new ArrayList<>();
        Matcher matcher = WORD.matcher(phrase);
        while (matcher.find()) {
            String key = matcher.group().toLowerCase();
            boolean known = dictionary.containsKey(key) || ARTICLES.contains(key);
            if (!known && !unknown.contains(key)) {
                unknown.add(key);
            }
        }
        return unknown;
    }

    /** Копiя словника, вiдсортована за англiйськими словами, — для виведення на екран. */
    public Map<String, String> getSortedDictionary() {
        return new TreeMap<>(dictionary);
    }

    public int size() {
        return dictionary.size();
    }

    private static boolean hasSentenceEnd(String text) {
        return text.indexOf('.') >= 0 || text.indexOf('!') >= 0 || text.indexOf('?') >= 0;
    }

    private static String capitalize(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
