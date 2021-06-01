/*
Задание: Дан массив с числами. Проверьте, есть ли в нем два одинаковых числа подряд. Если есть - выведите 'да', а если нет - выведите 'нет'.
*/

public class Main {
    public static void main(String[] args) {
        checkseq(new int[] {1, 2, 3, 3, 5});
        checkseq(new int[] {1, 2, 3, 2, 2});
        checkseq(new int[] {1, 1});
        checkseq(new int[] {1, 5, 1, 5, 1});
        checkseq(new int[] {9});
        checkseq(new int[] {});
    }

    public static void checkseq(int a[]) {
        for (int i = 0; i + 1 < a.length; ++i) {
            if (a[i] == a[i + 1]) {
                System.out.println("да");
                return;
            }
        }
        System.out.println("нет");
    }
}
