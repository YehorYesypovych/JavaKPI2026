package iostreams.task3;

import iostreams.common.ConsoleIO;

import java.io.FileNotFoundException;
import java.io.IOException;

/** Завдання 5.3: шифрування символьного потоку за допомогою FilterWriter / FilterReader. */
public class CipherApp {
    private static final CipherFileManager files = new CipherFileManager();

    public static void main(String[] args) {
        while (true) {
            ConsoleIO.println("\n=== ШИФРУВАННЯ ===");
            ConsoleIO.println("1. Зашифрувати файл");
            ConsoleIO.println("2. Розшифрувати файл");
            ConsoleIO.println("3. Зашифрувати рядок з клавiатури");
            ConsoleIO.println("4. Вихiд");
            String choice = ConsoleIO.readLine("Оберiть дiю: ");

            try {
                switch (choice) {
                    case "1" -> processFile(true);
                    case "2" -> processFile(false);
                    case "3" -> processString();
                    case "4" -> {
                        ConsoleIO.println("Роботу завершено.");
                        return;
                    }
                    default -> ConsoleIO.println("Невiдома команда.");
                }
            } catch (FileNotFoundException e) {
                ConsoleIO.println("Помилка: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                ConsoleIO.println("Некоректне введення: " + e.getMessage());
            } catch (IOException e) {
                ConsoleIO.println("Помилка вводу-виводу: " + e.getMessage());
            }
        }
    }

    private static void processFile(boolean encrypt) throws IOException {
        String source = ConsoleIO.readLine("Вхiдний файл: ");
        String target = ConsoleIO.readLine("Вихiдний файл: ");
        if (source.isEmpty() || target.isEmpty()) {
            throw new IllegalArgumentException("шлях до файлу не може бути порожнiм");
        }
        char key = readKey();

        long count = encrypt
                ? files.encryptFile(source, target, key)
                : files.decryptFile(source, target, key);

        ConsoleIO.println((encrypt ? "Зашифровано " : "Розшифровано ") + count + " символiв у файл " + target);
        ConsoleIO.println("Початок результату: " + files.preview(target, 60));
    }

    private static void processString() throws IOException {
        String text = ConsoleIO.readLine("Введiть текст: ");
        char key = readKey();
        String encrypted = CharCipher.encrypt(text, key);
        ConsoleIO.println("Зашифровано:  " + encrypted);
        ConsoleIO.println("Розшифровано: " + CharCipher.decrypt(encrypted, key));
    }

    /** Ключ — рівно один непробільний символ. */
    private static char readKey() {
        String key = ConsoleIO.readLine("Ключовий символ: ");
        if (key.length() != 1) {
            throw new IllegalArgumentException("ключ має складатися рiвно з одного символу");
        }
        char c = key.charAt(0);
        ConsoleIO.println("Код ключового символу '" + c + "' = " + (int) c);
        return c;
    }
}
