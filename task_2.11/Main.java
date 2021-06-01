/*
Задание: Дан массив с числами. Узнайте сколько элементов с начала массива надо сложить, чтобы в сумме получилось больше 10-ти.
*/

public class Main {
    public static void main(String[] args) {
        countpast10(new int[] {});
        countpast10(new int[] {1, 2});
        countpast10(new int[] {1, 2, 3, 4});
        countpast10(new int[] {10, -1, 2});
        countpast10(new int[] {10, 1, 2, 5, 6});
        countpast10(new int[] {111, 3, 3, 3});
    }

    public static void countpast10(int a[]) {
        int sum = 0;
        for (int i = 0; i < a.length; ++i) {
            sum += a[i];
            if (sum > 10) {
                System.out.format("сложить %d\n", i + 1);
                return;
            }
        }
        System.out.println("невозможно получить сумму больше 10");
    }
}
