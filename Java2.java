import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

class CuratorRecord {
    String lastName;
    String firstName;
    String birthDate;
    String phone;
    String address;

    public CuratorRecord(String lastName, String firstName, String birthDate, String phone, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " (" + birthDate + "), Тел: " + phone + ", Адреса: " + address;
    }

    @Override
    public boolean equals(Object obj) {
        CuratorRecord other = (CuratorRecord) obj;
        return this.phone.equals(other.phone);
    }
}

public class Java2 {
    public static void main(String[] args) throws Exception {
        
        System.setOut(new java.io.PrintStream(System.out, true, "cp866"));
        
        Scanner scanner = new Scanner(System.in, "cp866");
        
        ArrayList<CuratorRecord> journal = new ArrayList<>();
        Gson gson = new Gson();

        System.out.println("--- Журнал Куратора ---");

        while (true) {
            System.out.println("\nОберiть дiю: 1-Додати, 2-Показати, 3-Зберегти, 4-Завантажити, 5-Вийти");
            System.out.print("> ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                
                String lastName = "";
                while (true) {
                    System.out.print("Введiть прiзвище: ");
                    lastName = scanner.nextLine();
                    if (lastName.matches("^[a-zA-Zа-яА-ЯєЄiiїЇґҐ'\\- ]+$")) {
                        break;
                    } else {
                        System.out.println("Помилка: Прiзвище має мiстити лише лiтери!");
                    }
                }

                String firstName = "";
                while (true) {
                    System.out.print("Введiть iм'я: ");
                    firstName = scanner.nextLine();
                    if (firstName.matches("^[a-zA-Zа-яА-ЯєЄiiїЇґҐ'\\- ]+$")) {
                        break;
                    } else {
                        System.out.println("Помилка: iм'я має мiстити лише лiтери!");
                    }
                }

                String birthDate = "";
                while (true) {
                    System.out.print("Введiть дату (формат 01.01.2005): ");
                    birthDate = scanner.nextLine();
                    if (birthDate.matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
                        break;
                    } else {
                        System.out.println("Помилка: Дата не може мiстити лiтери! Використовуйте цифри та крапки.");
                    }
                }

                String phone = "";
                while (true) {
                    System.out.print("Введiть телефон: ");
                    phone = scanner.nextLine();
                    if (phone.matches("^\\+?\\d{10,15}$")) {
                        break;
                    } else {
                        System.out.println("Помилка: Телефон має мiстити лише цифри (без лiтер) i бути не коротшим за 10 символiв!");
                    }
                }

                String address = "";
                while (true) {
                    System.out.print("Введiть адресу (вулиця, будинок, квартира): ");
                    address = scanner.nextLine();
                    
                    String[] parts = address.split(",");
                    
                    if (address.trim().isEmpty()) {
                        System.out.println("Помилка: Адреса не може бути порожньою.");
                    } 
                    else if (parts[0].matches(".*\\d.*")) {
                        System.out.println("Помилка: Назва вулицi не може мiстити цифри! Пишiть у форматi: Урлiвська, 38, 534");
                    } else {
                        break;
                    }
                }

                CuratorRecord newRecord = new CuratorRecord(lastName, firstName, birthDate, phone, address);
                
                boolean exists = false;
                for (int i = 0; i < journal.size(); i++) {
                    if (journal.get(i).equals(newRecord)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Студент з таким телефоном вже є в базi!");
                } else {
                    journal.add(newRecord);
                    System.out.println("Запис успiшно додано!");
                }

            } else if (choice.equals("2")) {
                if (journal.isEmpty()) {
                    System.out.println("Журнал порожнiй.");
                } else {
                    for (int i = 0; i < journal.size(); i++) {
                        System.out.println((i + 1) + ". " + journal.get(i).toString());
                    }
                }

            } else if (choice.equals("3")) {
                try {
                    FileWriter writer = new FileWriter("journal.json", StandardCharsets.UTF_8);
                    gson.toJson(journal, writer);
                    writer.close();
                    System.out.println("Данi збережено у journal.json");
                } catch (Exception e) {
                    System.out.println("Помилка запису: " + e.getMessage());
                }

            } else if (choice.equals("4")) {
                try {
                    FileReader reader = new FileReader("journal.json", StandardCharsets.UTF_8);
                    CuratorRecord[] loadedData = gson.fromJson(reader, CuratorRecord[].class);
                    
                    if (loadedData != null) {
                        journal.clear();
                        for (int i = 0; i < loadedData.length; i++) {
                            journal.add(loadedData[i]);
                        }
                        System.out.println("Завантажено записiв: " + journal.size());
                    }
                    reader.close();
                } catch (Exception e) {
                    System.out.println("Помилка читання (можливо файл ще не створено): " + e.getMessage());
                }

            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("Невiдома команда.");
            }
        }
    }
}