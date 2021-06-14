
/*
1. Ввести путь к файлу с консоли.
2. Прочитать из него набор чисел.
3. Вывести на консоль только четные, отсортированные по возрастанию.
Пример ввода:
5
8
-2
11
3
-5
2
10
Пример вывода:
-2
2
8
10
*/

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите имя файла:");
        String filename = new Scanner(System.in).next();
        try {
            Scanner s = new Scanner(new File(filename));
            ArrayList<Integer> nums = new ArrayList<Integer>();
            while (s.hasNextInt()) {
                nums.add(s.nextInt());
            }
            Collections.sort(nums);
            for (int i = 0; i < nums.size(); i++)
                if (nums.get(i) % 2 == 0)
                    System.out.println(nums.get(i));
        } catch (IOException e) {
            System.out.println("Ошибка при открытии файла " + filename + ": " + e);
        }
    }
}
