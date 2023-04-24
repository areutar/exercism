public class DoublyLinkedList<T> {
    public static final String LIST_IS_EMPTY = "List is empty!";
    private Node<T> head = null;
    private Node<T> tail = null;
    // firstly adding tail then head
    //so firstly removing head then tail

    public void push(T t) {
        if (tail == null) {
            tail = new Node<>(t, null, null);
        } else {
            Node<T> oldTail = tail;
            tail = new Node<>(t, oldTail, null);
            oldTail.setPrevious(tail);
            if (head == null) {
                head = oldTail;
            }
        }
    }

    public T pop() throws UnsupportedOperationException {
        if (tail == null) {
            throw new UnsupportedOperationException(LIST_IS_EMPTY);
        }
        Node<T> newTail = tail.getNext();
        T t = tail.getT();
        if (newTail != null) {
            tail = newTail;
            newTail.setPrevious(null);
            if (newTail.equals(head)) {
                head = null;
            }
        }
        return t;
    }

    public T shift() throws UnsupportedOperationException {
        if (tail == null) {
            throw new UnsupportedOperationException(LIST_IS_EMPTY);
        }

        if (head == null) {
            T t = tail.getT();
            tail = null;
            return t;
        } else {
            Node<T> newHead = head.getPrevious();
            T t = head.getT();
            if (newHead.equals(tail)) {
                head = null;
            } else {
                head = newHead;
            }
            return t;
        }
    }

    public void unshift(T t) {
        if (tail == null) {
            tail = new Node<>(t, null, null);
        } else {
            if (head == null) {
                head = new Node<>(t, null, tail);
                tail.setNext(head);
            } else {
                Node<T> oldHead = head;
                head = new Node<>(t, null, oldHead);
                oldHead.setNext(head);
            }
        }
    }
}