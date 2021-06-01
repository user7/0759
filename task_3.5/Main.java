/*
Задание: Дан массив с числами. Выведите последовательно его элементы используя рекурсию и не используя цикл.
*/


public class Main {
    public static void main(String[] args) {
        int num[] = {4,2,6,2,65};
        printRec(num, 0);
    }

    public static void printRec(int arr[], int i) {
        if (i < arr.length) {
            System.out.println(arr[i]);
            printRec(arr, i + 1);
        }
    }
}
