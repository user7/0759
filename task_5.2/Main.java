/*
Задача: Программа определяет, какая семья (фамилию) живёт в доме с указанным номером.
Новая задача: Программа должна работать не с номерами домов, а с городами:
Пример ввода:
Москва
Ивановы
Киев
Петровы
Лондон
Абрамовичи
Лондон
Пример вывода:
Абрамовичи */


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/*
Модернизация ПО
*/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HashMap<String, String> list = new HashMap<String, String>();
        while (true) {
            String city = scanner.next();
            String family = scanner.next();
            // знак ? вместо family означает конец ввода, например на входе:
            //   лондон ивановы москва петровы москва ?
            // тогда на выходе:
            //   петровы
            if (family.equals("?")) {
                family = list.get(city);
                if (family != null)
                    System.out.println(family);
                break;
            }
            list.put(city, family);
        }
    }
}
