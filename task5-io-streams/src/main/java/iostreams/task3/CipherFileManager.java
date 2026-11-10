package iostreams.task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/** Клас для роботи з файлами: шифрування і дешифрування текстових файлів. */
public class CipherFileManager {

    /** Шифрує файл: читаємо звичайний текст, записуємо через CipherWriter. */
    public long encryptFile(String source, String target, char key) throws IOException {
        checkPaths(source, target);
        try (Reader in = openReader(source);
             Writer out = new CipherWriter(openWriter(target), key)) {
            return transfer(in, out);
        }
    }

    /** Дешифрує файл: читаємо через CipherReader, записуємо звичайний текст. */
    public long decryptFile(String source, String target, char key) throws IOException {
        checkPaths(source, target);
        try (Reader in = new CipherReader(openReader(source), key);
             Writer out = openWriter(target)) {
            return transfer(in, out);
        }
    }

    /** Повертає перші maxChars символів файлу для попереднього перегляду. */
    public String preview(String path, int maxChars) throws IOException {
        try (Reader in = openReader(path)) {
            char[] buf = new char[maxChars];
            int n = in.read(buf);
            return n <= 0 ? "" : new String(buf, 0, n);
        }
    }

    private long transfer(Reader in, Writer out) throws IOException {
        char[] buffer = new char[4096];
        long total = 0;
        int n;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
            total += n;
        }
        return total;
    }

    private void checkPaths(String source, String target) throws IOException {
        File src = new File(source);
        if (!src.isFile()) {
            throw new FileNotFoundException("файл не знайдено - " + source);
        }
        if (src.getCanonicalFile().equals(new File(target).getCanonicalFile())) {
            throw new IllegalArgumentException("вхiдний i вихiдний файли мають бути рiзними");
        }
    }

    private Reader openReader(String path) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
    }

    private Writer openWriter(String path) throws IOException {
        File dir = new File(path).getAbsoluteFile().getParentFile();
        if (dir != null && !dir.exists() && !dir.mkdirs()) {
            throw new IOException("не вдалося створити каталог " + dir);
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
    }
}
