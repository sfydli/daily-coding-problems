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
}
