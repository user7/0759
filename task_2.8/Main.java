/*
Задание: Дан массив с элементами [2, 3, 4, 5]. С помощью цикла for найдите произведение элементов этого массива.
*/

public class Main {
    public static void main(String[] args) {
        int a[] = new int[] {2, 3, 4, 5};
        int p = 1;
        for (int i = 0; i < a.length; ++i) {
            p *= a[i];
        }
        System.out.println(p);
    }
}
