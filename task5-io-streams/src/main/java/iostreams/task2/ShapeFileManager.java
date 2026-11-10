package iostreams.task2;

import iostreams.task2.model.Shape;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Окремий клас для роботи з файлами: збереження і читання набору фігур. */
public class ShapeFileManager {

    /** Зберігає масив фігур у файл за допомогою об'єктного потоку (серіалізація за замовчуванням). */
    public void save(Shape[] shapes, String path) throws IOException {
        File file = new File(path);
        File dir = file.getAbsoluteFile().getParentFile();
        if (dir != null && !dir.exists() && !dir.mkdirs()) {
            throw new IOException("Не вдалося створити каталог " + dir);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeObject(shapes);
        }
    }

    /** Читає масив фігур з файлу (десеріалізація). */
    public Shape[] load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Object data = in.readObject();
            if (!(data instanceof Shape[] shapes)) {
                throw new InvalidObjectException("Файл не мiстить набору фiгур");
            }
            return shapes;
        }
    }
}
