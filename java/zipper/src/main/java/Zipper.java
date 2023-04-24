class Zipper {
    public Zipper left;
    public Zipper right;
    public Zipper up;
    private int value;
    private BinaryTree binaryTree;

    Zipper(int value) {
        this.value = value;
    }

    BinaryTree toTree() {
        return binaryTree;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    public void setBinaryTree(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    void setLeft(Zipper leftChild) {
        left = leftChild;
        if (left != null) {
            left.up = this;
            left.binaryTree = binaryTree;
        }
    }

    void setRight(Zipper rightChild) {
        right = rightChild;
        if (right != null) {
            right.up = this;
            right.binaryTree = binaryTree;
        }
    }

    @Override
    public String toString() {
        return String.format("{ value: %s, left: %s, right: %s }", value, left, right);
    }
}

class BinaryTree {
    private final Zipper root;

    BinaryTree(int value) {
        this(new Zipper(value));
    }

    BinaryTree(Zipper root) {
        this.root = root;
        root.setBinaryTree(this);
    }

    Zipper getRoot() {
        return root;
    }

    String printTree() {
        String rString = root.toString();
        return rString.substring(2, rString.length() - 2);
    }
}