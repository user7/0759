/*
* Дана коллекция имён.
* 1) удалить все повторяющиеся имена из коллекции
* 2) вывести коллекцию на экран
* */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Анатолий");
        names.add("Георгий");
        names.add("Руслан");
        names.add("Георгий");
        names.add("Павел");
        names.add("Руслан");

        HashSet<String> set = new HashSet<String>();
        ArrayList<String> names2 = new ArrayList<String>();
        Iterator<String> iter = names.iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (!set.contains(name)) {
                names2.add(name);
                set.add(name);
            }
        }
        names = names2;
        System.out.println(names);
    }
}
