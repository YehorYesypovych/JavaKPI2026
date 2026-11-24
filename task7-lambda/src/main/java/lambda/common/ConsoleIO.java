package lambda.common;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Спiльний клас для роботи з консоллю.
 * Кодування береться з властивостей JVM: у термiналi Windows це cp866,
 * в IntelliJ IDEA — UTF-8, тому кирилиця коректно читається i виводиться в обох випадках.
 */
public final class ConsoleIO {
    public static final String ENCODING = detectEncoding();

    private static final PrintStream OUT = createOut();
    private static final Scanner IN = new Scanner(System.in, ENCODING);

    private ConsoleIO() {
    }

    private static String detectEncoding() {
        String enc = System.getProperty("stdout.encoding");          // Java 18+
        if (enc == null) {
            enc = System.getProperty("sun.stdout.encoding");         // Java 17
        }
        if (enc == null || !Charset.isSupported(enc)) {
            enc = Charset.defaultCharset().name();
        }
        return enc;
    }

    private static PrintStream createOut() {
        try {
            return new PrintStream(new FileOutputStream(FileDescriptor.out), true, ENCODING);
        } catch (Exception e) {
            return System.out;
        }
    }

    public static void println(String text) {
        OUT.println(text);
    }

    public static void print(String text) {
        OUT.print(text);
        OUT.flush();
    }

    /** Виводить пiдказку i повертає введений рядок без пробiлiв на краях. */
    public static String readLine(String prompt) {
        print(prompt);
        return IN.nextLine().trim();
    }
}
