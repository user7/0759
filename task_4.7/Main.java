/*
~ Реализуй в классе Fox интерфейс Animal.
+ Поменяй код так, чтобы в классе Fox был только один метод - getName.
+ Учти, что создавать дополнительные классы и удалять методы нельзя!

Требования:
•	~ Интерфейс Animal должен быть реализован в классе Fox.
•	+ В интерфейсе Animal нужно объявить метод getColor.
•	+ Дополнительные классы или интерфейсы создавать нельзя.
*/

// Непонятная постановка задачи:
//
//  1. В Animal должен быть метод getColor
//  2. Fox должен реализовать Animal
//  3. "в классе Fox должен быть только один метод - getName"
//
// Из 1 и 2 следует, что в Fox есть метод getColor. Но согласно 3
// у Fox только один метод - getName, противоречие.
//
// Я сделал Fox абстрактным, реализиция getColor в нём отсутствует,
// может быть это имелось в виду?

public class Main {
    public static void main(String[] args) {
    }
}

interface Animal {
    public String getColor();
}

abstract class Fox implements Animal {
    public String getName() {
        return "Fox";
    }
}
