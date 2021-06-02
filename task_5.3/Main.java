/*
* Задание: Дана коллекция с числами. Запишите в новую коллекцию только те числа, которые больше нуля и меньше 10-ти.
* Коллекции вы создаёте сами
*/

import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(1, 4, -9, 12, -1, 0, 10, 7));
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i = 0; i < a.size(); i++)
            if (a.get(i) > 0 && a.get(i) < 10)
                b.add(a.get(i));
        System.out.println(b);
    }
}
