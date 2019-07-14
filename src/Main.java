import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Problem #1
        /*System.out.println(solution1(new int[] {10, 15, 3, 7}, 17));*/

        // Problem #2
        /*System.out.println(Arrays.toString(solution2_1(new int[]{1, 2, 3, 4, 5})));
        System.out.println(Arrays.toString(solution2_2(new int[]{1, 2, 3, 4, 5})));*/

        // Problem #3
        /*Node<String> node = new Node<>(
                "root",
                new Node<>(
                        "left",
                        new Node<>("left.left"),
                        null),
                new Node<>("right"));
        assert Node.deserialize(node.serialize()).getLeft().getLeft().getValue() == "left.left";*/

        // Problem #5
        /*System.out.println(car(cons(3, 4)));
        System.out.println(cdr(cons(3, 4)));*/

        // Problem #7
        /*System.out.println(solution7("111"));*/

        // Problem #8
        /*Node<Integer> node = new Node<>(
                0,
                new Node<>(1),
                new Node<>(
                        0,
                        new Node<>(
                                1,
                                new Node<>(1),
                                new Node<>(1)),
                        new Node<>(0)));
        assert node.getNumberOfUnivalSubtrees() == 5;
        System.out.println(node.getNumberOfUnivalSubtrees());*/

        // Problem #222
        /*System.out.println(solution222("/usr/bin/../bin/./scripts/../"));
        System.out.println(solution222("/usr/../usr/bin/../bin/./langs/python/../java"));*/

        // Problem #214
        /*System.out.println(solution214(156));
        System.out.println(solution214(1983));*/
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

    public static int solution7(String message) {
        if (message.indexOf('0') != -1) {
            throw new IllegalArgumentException("Zero is not allowed in the message content!");
        }
        if (message.length() <= 1) {
            return 1;
        } else {
            return Integer.parseInt(message.substring(0, 2)) <= 26
                    ? solution7(message.substring(1)) + solution7(message.substring(2))
                    : solution7(message.substring(1));
        }
    }

    public static String solution222(String path) {
        Stack<String> tokens = new Stack<>();
        for (String token : path.split("/")) {
            if (token.equals("..")) {
                tokens.pop();
            } else if (!token.equals(".")) {
                tokens.push(token);
            }
        }
        return tokens.stream().collect(Collectors.joining("/", "", "/"));
    }

    public static int solution214(int number) {
        int max = 0;
        int repetitions = 0;
        while (number != 0) {
            if (number % 2 == 1) {
                number--;
                repetitions++;
            } else {
                repetitions = 0;
            }
            if (repetitions > max) {
                max = repetitions;
            }
            number /= 2;
        }
        return max;
    }
}
