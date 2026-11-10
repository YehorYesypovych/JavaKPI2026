package iostreams.task3;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/** Фільтруючий символьний потік введення: дешифрує кожен прочитаний символ. */
public class CipherReader extends FilterReader {
    private final char key;

    public CipherReader(Reader in, char key) {
        super(in);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return c == -1 ? -1 : CharCipher.decrypt((char) c, key);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count = super.read(cbuf, off, len);
        for (int i = off; i < off + count; i++) {
            cbuf[i] = CharCipher.decrypt(cbuf[i], key);
        }
        return count;
    }
}
