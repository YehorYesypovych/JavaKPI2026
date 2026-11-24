package collections.task1;

import collections.common.ConsoleIO;

import java.util.List;
import java.util.Map;

/** Simple Task: консольний перекладач з англiйської мови на українську. */
public class TranslatorApp {

    /** Початковий вмiст словника: пари «англiйське слово — переклад». */
    private static final String[][] DEFAULT_WORDS = {
            {"i", "я"}, {"you", "ти"}, {"he", "вiн"}, {"she", "вона"}, {"we", "ми"},
            {"they", "вони"}, {"it", "це"}, {"my", "мiй"}, {"your", "твiй"},
            {"am", "є"}, {"is", "є"}, {"are", "є"}, {"not", "не"}, {"don't", "не"},
            {"love", "люблю"}, {"like", "люблю"}, {"have", "маю"}, {"read", "читаю"},
            {"write", "пишу"}, {"study", "вивчаю"}, {"hello", "привiт"}, {"world", "свiт"},
            {"cat", "кiт"}, {"dog", "собака"}, {"book", "книга"}, {"table", "стiл"},
            {"house", "будинок"}, {"student", "студент"}, {"teacher", "викладач"},
            {"university", "унiверситет"}, {"programming", "програмування"},
            {"language", "мова"}, {"java", "Java"}, {"kyiv", "Київ"}, {"ukraine", "Україна"},
            {"good", "добрий"}, {"big", "великий"}, {"interesting", "цiкавий"},
            {"very", "дуже"}, {"and", "i"}, {"on", "на"}, {"in", "у"}, {"from", "з"}
    };

    public static void main(String[] args) {
        Translator translator = new Translator();
        for (String[] pair : DEFAULT_WORDS) {
            translator.addWord(pair[0], pair[1]);
        }
        ConsoleIO.println("=== ПЕРЕКЛАДАЧ З АНГЛiЙСЬКОЇ НА УКРАЇНСЬКУ ===");
        ConsoleIO.println("Слiв у початковому словнику: " + translator.size());

        while (true) {
            ConsoleIO.println("");
            ConsoleIO.println("1. Поповнити словник з клавiатури");
            ConsoleIO.println("2. Перекласти фразу");
            ConsoleIO.println("3. Показати словник");
            ConsoleIO.println("4. Вихiд");
            switch (ConsoleIO.readLine("Оберiть дiю: ")) {
                case "1" -> addWords(translator);
                case "2" -> translatePhrase(translator);
                case "3" -> printDictionary(translator);
                case "4" -> {
                    ConsoleIO.println("Роботу завершено.");
                    return;
                }
                default -> ConsoleIO.println("Невiдома команда. Введiть число вiд 1 до 4.");
            }
        }
    }

    /** Введення пар у форматi english=українська; порожнiй рядок завершує введення. */
    private static void addWords(Translator translator) {
        ConsoleIO.println("Вводьте пари у форматi english=українська (Enter — завершити):");
        int count = 0;
        String line;
        while (!(line = ConsoleIO.readLine("> ")).isEmpty()) {
            String[] parts = line.split("=", 2);
            if (parts.length < 2) {
                ConsoleIO.println("  Неправильний формат, приклад: apple=яблуко");
                continue;
            }
            try {
                boolean added = translator.addWord(parts[0], parts[1]);
                ConsoleIO.println(added ? "  Додано." : "  Переклад оновлено.");
                count++;
            } catch (IllegalArgumentException e) {
                ConsoleIO.println("  Помилка: " + e.getMessage());
            }
        }
        ConsoleIO.println("Збережено пар: " + count + ", слiв у словнику: " + translator.size());
    }

    private static void translatePhrase(Translator translator) {
        String phrase = ConsoleIO.readLine("Введiть фразу англiйською: ");
        if (phrase.isEmpty()) {
            ConsoleIO.println("Фразу не введено.");
            return;
        }
        ConsoleIO.println("Переклад: " + translator.translate(phrase));

        List<String> unknown = translator.findUnknownWords(phrase);
        if (!unknown.isEmpty()) {
            ConsoleIO.println("Немає у словнику: " + String.join(", ", unknown));
        }
    }

    /** Виводить словник за алфавiтом у три стовпцi. */
    private static void printDictionary(Translator translator) {
        ConsoleIO.println("Словник (слiв: " + translator.size() + "):");
        StringBuilder row = new StringBuilder();
        int column = 0;
        for (Map.Entry<String, String> entry : translator.getSortedDictionary().entrySet()) {
            row.append(String.format("%-30s", entry.getKey() + " — " + entry.getValue()));
            if (++column % 3 == 0) {
                ConsoleIO.println(row.toString().stripTrailing());
                row.setLength(0);
            }
        }
        if (row.length() > 0) {
            ConsoleIO.println(row.toString().stripTrailing());
        }
    }
}
