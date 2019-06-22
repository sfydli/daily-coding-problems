### Daily Coding Problems

- [Daily Coding Problems](#daily-coding-problems)
    + [1. Sum check of two numbers](#1-sum-check-of-two-numbers)
    + [2. Array of non-index products](#2-array-of-non-index-products)

##### 1. Sum check of two numbers

~~~~
Given a list of numbers and a number k, return whether any two numbers from the list add up to k.

For example, given [10, 15, 3, 7] and k of 17, return true since 10 + 7 is 17.

Bonus: Can you do this in one pass?
~~~~

> Bonus solution

````java
public static boolean solution(int[] numbers, int k) {
    HashSet<Integer> reminders = new HashSet<>();
    for (int number : numbers) {
        if (reminders.contains(number)) {
            return true;
        } else if (number < k) {
            reminders.add(k - number);
        }
    }
    return false;
}
````

##### 2. Array of non-index products

~~~~
Given an array of integers, return a new array such that each element at index i of the new array is the product of all the numbers in the original array except the one at i.
      
For example, if our input was [1, 2, 3, 4, 5], the expected output would be [120, 60, 40, 30, 24]. If our input was [3, 2, 1], the expected output would be [2, 3, 6].
      
Follow-up: what if you can't use division?
~~~~

> Normal solution

````java
public static int[] solution2_1(int[] numbers) {
    int product = Arrays.stream(numbers)
            .reduce(1, (number1, number2) -> number1 * number2);
    int[] result = new int[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
        result[i] = product / numbers[i];
    }
    return result;
}
````

> Bonus solution

````java
public static int[] solution2_2(int[] numbers) {
    int leftProduct = 1;
    int[] result = new int[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
        int rightProduct = Arrays.stream(numbers)
                .skip(i + 1)
                .reduce(1, (number1, number2) -> number1 * number2);
        result[i] = leftProduct * rightProduct;
        leftProduct *= numbers[i];
    }
    return result;
}
````