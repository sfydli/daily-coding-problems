import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        // Problem #1
        // System.out.println(solution1(new int[] {10, 15, 3, 7}, 17));

        // Problem #2
        // System.out.println(Arrays.toString(solution2_1(new int[]{1, 2, 3, 4, 5})));
        // System.out.println(Arrays.toString(solution2_2(new int[]{1, 2, 3, 4, 5})));

        // Problem #3
        /*Node<String> node = new Node<>(
                "root",
                new Node<>(
                        "left",
                        new Node<>("left.left"),
                        null),
                new Node<>("right"));
        assert Node.deserialize(node.serialize()).getLeft().getLeft().getValue() == "left.left";*/
        System.out.println(car(cons(3, 4)));
        System.out.println(cdr(cons(3, 4)));
    }

    public static boolean solution1(int[] numbers, int k) {
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

    public static int[] solution2_1(int[] numbers) {
        int product = Arrays.stream(numbers)
                .reduce(1, (number1, number2) -> number1 * number2);
        int[] result = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            result[i] = product / numbers[i];
        }
        return result;
    }

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

    public static Function<BiFunction<Integer, Integer, Integer>, Integer> cons(int a, int b) {
        return f -> f.apply(a, b);
    }

    public static int car(Function<BiFunction<Integer, Integer, Integer>, Integer> pair) {
        return pair.apply(Math::min);
    }

    public static int cdr(Function<BiFunction<Integer, Integer, Integer>, Integer> pair) {
        return pair.apply(Math::max);
    }
}
