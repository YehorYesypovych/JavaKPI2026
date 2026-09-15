import java.util.Scanner;

public class PerfectNumbers {
    public static void main(String[] args) {
        // перевірка на правильний ввід користувача
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введiть позитивне число (межу дiапазону): ");
        
        // 
        if (!scanner.hasNextInt()) {
            System.out.println("Ви ввели текст або некоректнi символи, а потрiбно цiле число!");
            scanner.close();
            return; 
        }
        
        int limit = scanner.nextInt();
        
        if (limit <= 0) {
            System.out.println("Межа дiапазону має бути бiльшою за 0!");
            scanner.close();
            return; 
        }
        
        System.out.println("Досконалi числа в дiапазонi вiд 1 до " + limit + ":");
        // зовнішній цикл перебирає всі числа від 1 до заданого числа
        for (int number = 1; number <= limit; number++) {
            int sum = 0; 
            // внутрішній цикл шукає всі дільники поточного числа
            for (int divisor = 1; divisor < number; divisor++) {
                if (number % divisor == 0) {
                    sum = sum + divisor; 
                }
            }
           // якщо сума дільників дорівнює самому числу, воно є досконалим 
            if (sum == number) {
                System.out.println(number);
            }
        }
        
        scanner.close();
    }
}