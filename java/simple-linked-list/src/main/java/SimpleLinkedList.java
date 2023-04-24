import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
class SimpleLinkedList<T> {
    Node<T>[] nodes;
    private int tailPos = -1;

    SimpleLinkedList() {
        nodes = new Node[10];
    }

    SimpleLinkedList(T[] values) {
        nodes = new Node[values.length];
        nodes[values.length - 1] = new Node<>(values[values.length - 1]);
        IntStream.iterate(values.length - 2, i -> i >= 0, i -> i - 1)
                .forEach(i -> nodes[i] = new Node<>(values[i], nodes[i + 1]));
        tailPos = values.length - 1;
    }

    void push(T value) {
        if (tailPos == nodes.length - 1) {
            expand();
        }
        nodes[++tailPos] = new Node<>(value);
        if (tailPos > 0) {
            nodes[tailPos - 1].setNext(nodes[tailPos]);
        }
    }

    T pop() {
        if (tailPos < 0) {
            throw new NoSuchElementException();
        }
        T toReturn = nodes[tailPos].getValue();
        nodes[tailPos] = null;
        tailPos--;
        if (tailPos < nodes.length / 2) {
            collapse();
        }
        return toReturn;
    }

    void reverse() {
        for (int i = 0; i <= tailPos / 2; i++) {
            Node<T> node = nodes[i];
            nodes[i] = nodes[tailPos - i];
            nodes[tailPos - i] = node;
        }
    }

    T[] asArray(Class<T> clazz) {
        return (T[]) IntStream.rangeClosed(0, tailPos)
                .mapToObj(i ->
                        tailPos >= 0 ? nodes[tailPos - i].getValue() : null
                )
                .toArray();
    }

    private void expand() {
        nodes = Arrays.copyOf(nodes, nodes.length * 3 / 2);
    }

    private void collapse() {
        nodes = Arrays.copyOf(nodes, nodes.length / 2);
    }

    int size() {
        return tailPos + 1;
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
