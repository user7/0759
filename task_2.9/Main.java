/*
Задание: Дан массив с числами. Проверьте, что в этом массиве есть число 5. Если есть - выведите 'да', а если нет - выведите 'нет'.
*/

public class Main {
    public static void main(String[] args) {
        check5(new int[] {1, 2, 3, 4, 5});
        check5(new int[] {1, 2, 3, 4});
        check5(new int[] {1, 5, 3});
    }

    public static void check5(int a[]) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == 5) {
                System.out.println("да");
                return;
            }
        }
        System.out.println("нет");
    }
}
