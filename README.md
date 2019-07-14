### Daily Coding Problems

- [Daily Coding Problems](#daily-coding-problems)
    + [1. Sum check of two numbers](#1-sum-check-of-two-numbers)
    + [2. Array of non-index products](#2-array-of-non-index-products)
    + [3. Binary tree serialization-deserialization](#3-binary-tree-serialization-deserialization)
    + [5. Functional min-max implementation](#5-functional-min-max-implementation)
    + [7. Number of decoding methods](#7-number-of-decoding-methods)
    + [8. Count of unival subtrees](#8-count-of-unival-subtrees)
    + [222. Shortest absolute path](#222-shortest-absolute-path)

##### 1. Sum check of two numbers 

~~~~
Given a list of numbers and a number k, return whether any two numbers from the list add up to k.

For example, given [10, 15, 3, 7] and k of 17, return true since 10 + 7 is 17.

Bonus: Can you do this in one pass?
~~~~

<details>
<summary>Bonus solution</summary>

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
</details>

##### 2. Array of non-index products

~~~~
Given an array of integers, return a new array such that each element at index i of the new array is the product of all the numbers in the original array except the one at i.
      
For example, if our input was [1, 2, 3, 4, 5], the expected output would be [120, 60, 40, 30, 24]. If our input was [3, 2, 1], the expected output would be [2, 3, 6].
      
Follow-up: what if you can't use division?
~~~~

<details>
<summary>Normal solution</summary>

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
</details>

<details>
<summary>Bonus solution</summary>

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
</details>

##### 3. Binary tree serialization-deserialization

~~~~
This problem was asked by Google.

Given the root to a binary tree, implement serialize(root), which serializes the tree into a string, and deserialize(s), which deserializes the string back into the tree.

For example, given the following Node class

class Node:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right
The following test should pass:

node = Node('root', Node('left', Node('left.left')), Node('right'))
assert deserialize(serialize(node)).left.left.val == 'left.left'
~~~~

<details>
<summary>Solution</summary>

````java
public class Node<T> {

    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Node(T value) {
        this(value, null, null);
    }

    private Node() {}

    public T getValue() {
        return value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public String serialize() {
        return serialize(this, "");
    }

    private String serialize(Node<T> node, String string) {
        if (node != null && node.value != null) {
            return String.format("%s%s-%s%s", string, node.value.toString(), serialize(node.left, string), serialize(node.right, string));
        }
        return string + "null-";
    }

    public static <T> Node<T> deserialize(String string) {
        Node<T> node = new Node<>();
        deserialize(string, node);
        return node;
    }

    private static <T> String deserialize(String string, Node<T> node) {
        int delimiterIndex = string.indexOf('-');
        if (delimiterIndex == -1) {
            return "";
        }
        String value = string.substring(0, delimiterIndex);
        if (value.equals("null")) {
            return string.substring(delimiterIndex + 1);
        }
        node.value = (T) value;
        node.left = new Node<>();
        node.right = new Node<>();
        string = deserialize(string.substring(delimiterIndex + 1), node.left);
        string = deserialize(string, node.right);
        return string;
    }
}
````
</details>

##### 5. Functional min-max implementation

~~~~
This problem was asked by Jane Street.

cons(a, b) constructs a pair, and car(pair) and cdr(pair) returns the first and last element of that pair. For example, car(cons(3, 4)) returns 3, and cdr(cons(3, 4)) returns 4.

Given this implementation of cons:

def cons(a, b):
    def pair(f):
        return f(a, b)
    return pair
    
Implement car and cdr.
~~~~

<details>
<summary>Solution</summary>

````java
public static Function<BiFunction<Integer, Integer, Integer>, Integer> cons(int a, int b) {
    return f -> f.apply(a, b);
}

public static int car(Function<BiFunction<Integer, Integer, Integer>, Integer> pair) {
    return pair.apply(Math::min);
}

public static int cdr(Function<BiFunction<Integer, Integer, Integer>, Integer> pair) {
    return pair.apply(Math::max);
}
````
</details>

##### 7. Number of decoding methods

~~~~
This problem was asked by Facebook.

Given the mapping a = 1, b = 2, ... z = 26, and an encoded message, count the number of ways it can be decoded.

For example, the message '111' would give 3, since it could be decoded as 'aaa', 'ka', and 'ak'.

You can assume that the messages are decodable. For example, '001' is not allowed.
~~~~

<details>
<summary>Solution</summary>

````java
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
````
</details>

##### 8. Count of unival subtrees

~~~~
This problem was asked by Google.

A unival tree (which stands for "universal value") is a tree where all nodes under it have the same value.

Given the root to a binary tree, count the number of unival subtrees.

For example, the following tree has 5 unival subtrees:

   0
  / \
 1   0
    / \
   1   0
  / \
 1   1
~~~~

<details>
<summary>Solution</summary>

````java
public int getNumberOfUnivalSubtrees() {
    return getNumberOfUnivalSubtrees(this).getKey();
}

private Pair<Integer, T> getNumberOfUnivalSubtrees(Node<T> node) {
    int total = 0;
    boolean isValueSame = true;
    if (node.left != null) {
        Pair<Integer, T> leftResult = getNumberOfUnivalSubtrees(node.left);
        isValueSame = leftResult.getValue() == node.value;
        total += leftResult.getKey();
    }
    if (node.right != null) {
        Pair<Integer, T> rightResult = getNumberOfUnivalSubtrees(node.right);
        isValueSame = isValueSame && rightResult.getValue() == node.value;
        total += rightResult.getKey();
    }
    total += isValueSame ? 1 : 0;
    return new Pair<>(total, isValueSame ? node.value : null);
}
````
</details>

##### 222. Shortest absolute path

~~~~
This problem was asked by Quora.

Given an absolute pathname that may have . or .. as part of it, return the shortest standardized path.

For example, given "/usr/bin/../bin/./scripts/../", return "/usr/bin/".
~~~~

<details>
<summary>Solution</summary>

````java
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
````

</details>