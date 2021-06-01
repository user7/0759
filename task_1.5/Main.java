/*
ВНИМАНИЕ! ДЛЯ РЕШЕНИЯ ЗАДАЧИ НЕОБХОДИМО ПРОСМОТРЕТЬ УРОК №4 https://www.youtube.com/watch?v=WmZ6kVTlWtg&list=PLOep0pi-UBExIxCNegkvCrBuDh0TE7Ihq&index=4
Программа запрашивает у пользователя значение в градусах Цельсия. Программа должена переводить температуру и выводить на экран
значение в градусах Фаренгейта.
Температура по Цельсию TC и температура по Фаренгейту TF связаны следующим соотношением:
TF = (9 / 5) * TC + 32
Пример:
на вход подается значение 41.
Пример вывода:
105.8 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите температуру в градусах Цельсия: ");
        float a = in.nextFloat();
        System.out.print("Температура в Фаренгейтах: ");
        System.out.println(a * 9 / 5 + 32);
    }
}
