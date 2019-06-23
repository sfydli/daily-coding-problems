import javafx.util.Pair;

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
}
