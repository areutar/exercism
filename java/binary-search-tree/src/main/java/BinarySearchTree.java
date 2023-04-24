import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    void insert(T value) {
        root = insert(root, value);
    }

    Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (node.data.compareTo(value) >= 0) {
            node.left = insert(node.left, value);
        }
        if (node.data.compareTo(value) < 0) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    List<T> getAsSortedList() {
        return getAsSortedList(root);
    }

    List<T> getAsSortedList(Node<T> node) {
        List<T> listLeft = node.left != null ?
                getAsSortedList(node.left) :
                Collections.emptyList();
        List<T> list2 = List.of(node.data);
        List<T> listRight = node.right != null ?
                getAsSortedList(node.right) :
                Collections.emptyList();
        return Stream.of(listLeft, list2, listRight).flatMap(Collection::stream).collect(Collectors.toList());
    }

    List<T> getAsLevelOrderList() {
        List<T> result = new ArrayList<>();
        Queue<Node<T>> queue = new ArrayDeque<>();
        Node<T> current = root;
        queue.add(current);
        while (!queue.isEmpty()) {
            current = queue.poll();
            result.add(current.data);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return result;
    }

    public Node<T> getRoot() {
        return root;
    }

    static class Node<T> {
        private final T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }
    }
}
