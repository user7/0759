/*
Задание: Даны переменные a и b. Проверьте, что a делится без остатка на b. Если это так - выведите 'Делится' и результат деления, иначе выведите 'Делится с остатком' и остаток от деления.
*/

public class Main {
    public static void main(String[] args) {
        divcheck(9, 3);
        divcheck(9, 5);
    }

    public static void divcheck(int a, int b) {
        int r = a % b;
        if (r == 0)
            System.out.println("Делится");
        else
            System.out.format("Делится с остатком %d\n", r);
    }
}
