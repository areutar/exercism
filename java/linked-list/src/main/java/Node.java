import java.util.Objects;

public class Node<T> {
    private T t;
    private Node<T> next;
    private Node<T> previous;

    public Node(T t, Node<T> next, Node<T> previous) {
        this.t = t;
        this.next = next;
        this.previous = previous;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(t, node.t) && Objects.equals(next, node.next) && Objects.equals(previous, node.previous);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, next, previous);
    }
}
