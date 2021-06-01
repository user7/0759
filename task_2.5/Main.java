/*
Задание: Запросить у пользователя ввод числа и сохранить это число в переменную a.Если переменная a равна 10, то выведите 'Верно', иначе выведите 'Неверно'.
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Угадайте число: ");
        if (new Scanner(System.in).nextInt() == 10)
            System.out.println("Верно");
        else
            System.out.println("Неверно");
    }
}
