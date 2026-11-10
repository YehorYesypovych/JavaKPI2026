package iostreams.task3;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Алгоритм шифрування: кожен символ замінюється на символ,
 * код якого більший на значення коду ключового символу.
 */
public final class CharCipher {

    private CharCipher() {
    }

    /** Шифрування одного символу: код + код ключа (за модулем 65536). */
    public static char encrypt(char c, char key) {
        return (char) (c + key);
    }

    /** Дешифрування одного символу: код − код ключа. */
    public static char decrypt(char c, char key) {
        return (char) (c - key);
    }

    /** Шифрує рядок, пропускаючи його через потік CipherWriter. */
    public static String encrypt(String text, char key) throws IOException {
        StringWriter result = new StringWriter();
        try (Writer writer = new CipherWriter(result, key)) {
            writer.write(text);
        }
        return result.toString();
    }

    /** Дешифрує рядок, читаючи його через потік CipherReader. */
    public static String decrypt(String text, char key) throws IOException {
        StringBuilder result = new StringBuilder();
        try (Reader reader = new CipherReader(new StringReader(text), key)) {
            int c;
            while ((c = reader.read()) != -1) {
                result.append((char) c);
            }
        }
        return result.toString();
    }
}
