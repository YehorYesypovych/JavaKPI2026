package iostreams.task1;

/** Результат пошуку: номер рядка у файлі, його текст і кількість слів. */
public record LineInfo(int number, String text, int wordCount) {
}
