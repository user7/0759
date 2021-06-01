/*
ВНИМАНИЕ эта задача для ДЗ и не является обязательной.
Задание: 1. Создай массив чисел.
2. Добавь в массив 10 чисел с клавиатуры.
3. Вывести на экран длину самой длинной последовательности повторяющихся чисел в списке.

Пример для списка 2, 4, 4, 4, 8, 8, 4, 12, 12, 14:
3
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a[] = new int [10];
        for (int i = 0; i < a.length; i++) {
            a[i] = s.nextInt();
        }
        int max = 0;
        int start = 0;
        for (int i = 0; i < a.length; i++) {
            int len;
            if (a[i] == a[start]) {
                len = i - start + 1;
            } else {
                len = 1;
                start = i;
            }
            if (len > max)
                max = len;
        }
        System.out.println(max);
    }
}
