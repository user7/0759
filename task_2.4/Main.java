/*
Задание: Пользователь вводит сумму вклада и процент, который будет начисляться ежегодно. Отобразить размер вклада поочередно на ближайшие 5 лет.
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int summ = s.nextInt(), percent = s.nextInt();
        for (int i = 0; i < 5; ++i) {
            summ = summ * (100 + percent) / 100;
            System.out.format("Через %d год(а): %d\n", i + 1, summ);
        }
    }
}
