package iostreams.task3;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/** Фільтруючий символьний потік виведення: шифрує кожен символ перед записом. */
public class CipherWriter extends FilterWriter {
    private final char key;

    public CipherWriter(Writer out, char key) {
        super(out);
        this.key = key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(CharCipher.encrypt((char) c, key));
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] encrypted = new char[len];
        for (int i = 0; i < len; i++) {
            encrypted[i] = CharCipher.encrypt(cbuf[off + i], key);
        }
        super.write(encrypted, 0, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }
}
