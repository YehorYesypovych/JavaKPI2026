package iostreams.task2;

/** Завдання 5.2: застосунок з КП №3, доповнений збереженням і читанням фігур з файлу. */
public class ShapesApp {
    public static void main(String[] args) {
        try {
            ShapeView view = new ShapeView();
            ShapeController controller = new ShapeController(view);
            controller.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
