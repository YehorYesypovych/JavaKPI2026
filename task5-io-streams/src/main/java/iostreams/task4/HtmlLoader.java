package iostreams.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/** Клас для завантаження HTML-сторінки за URL через потоки введення. */
public class HtmlLoader {
    private static final int MAX_REDIRECTS = 5;

    public String load(String address) throws IOException {
        if (!address.contains("://")) {
            address = "https://" + address;          // дозволяємо вводити адресу без протоколу
        }
        URL url = URI.create(address).toURL();       // IllegalArgumentException — некоректний URL

        for (int i = 0; i <= MAX_REDIRECTS; i++) {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(15_000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java IO Streams lab)");

            if (conn instanceof HttpURLConnection http) {
                int code = http.getResponseCode();
                if (code >= 300 && code < 400 && http.getHeaderField("Location") != null) {
                    url = URI.create(url.toString()).resolve(http.getHeaderField("Location")).toURL();
                    continue;                          // переходимо за перенаправленням
                }
                if (code >= 400) {
                    throw new IOException("сервер повернув код " + code);
                }
            }

            Charset charset = charsetOf(conn.getContentType());
            StringBuilder html = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charset))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    html.append(line).append('\n');
                }
            }
            return html.toString();
        }
        throw new IOException("забагато перенаправлень");
    }

    /** Визначає кодування сторінки із заголовка Content-Type (за замовчуванням UTF-8). */
    private Charset charsetOf(String contentType) {
        if (contentType != null) {
            for (String part : contentType.split(";")) {
                part = part.trim();
                if (part.toLowerCase().startsWith("charset=")) {
                    try {
                        return Charset.forName(part.substring(8).replace("\"", ""));
                    } catch (Exception ignored) {
                        // невідоме кодування — використаємо UTF-8
                    }
                }
            }
        }
        return StandardCharsets.UTF_8;
    }
}
