import java.util.*;

class Tree {
    private static final String TREE_COULD_NOT_BE_REORIENTED = "Tree could not be reoriented";
    private static final String NO_PATH_FOUND = "No path found";
    private final String label;
    private final List<Tree> children;

    public Tree(String label) {
        this(label, new ArrayList<>());
    }

    public Tree(String label, List<Tree> children) {
        this.label = label;
        this.children = children;
    }

    public static Tree of(String label) {
        return new Tree(label);
    }

    public static Tree of(String label, List<Tree> children) {
        return new Tree(label, children);
    }

    public List<Tree> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return label.equals(tree.label)
                && children.size() == tree.children.size()
                && children.containsAll(tree.children)
                && tree.children.containsAll(children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children);
    }

    @Override
    public String toString() {
        return "Tree{" + label +
                ", " + children +
                "}";
    }

    public Tree fromPov(String fromNode) {
        List<String> fromList = pathToRoot(this, fromNode);
        check(fromList, TREE_COULD_NOT_BE_REORIENTED);

        Tree result = this;
        for (String label : fromList) {
            if (label.equals(result.label)) {
                continue;
            }
            result = childPov(result, label);
        }
        return result;
    }

    public Tree childPov(Tree parent, String label) {
        Tree child = findTreeByLabel(parent.children, label);
        List<Tree> parenChildren = new ArrayList<>(parent.getChildren());
        List<Tree> childChildren = new ArrayList<>(child.getChildren());

        parenChildren.remove(child);
        Tree updatedParent = Tree.of(parent.label, parenChildren);
        childChildren.add(updatedParent);

        return Tree.of(child.label, childChildren);
    }

    private Tree findTreeByLabel(List<Tree> trees, String label) {
        for (Tree tree : trees) {
            if (tree.label.equals(label)) {
                return tree;
            }
        }
        throw new IllegalArgumentException();
    }

    public List<String> pathTo(String fromNode, String toNode) {
        List<String> fromList = pathToRoot(this, fromNode);
        check(fromList, NO_PATH_FOUND);
        List<String> toList = pathToRoot(this, toNode);
        check(toList, NO_PATH_FOUND);
        String mutualRoot = label;
        while (!fromList.isEmpty() && !toList.isEmpty() && fromList.get(0).equals(toList.get(0))) {
            mutualRoot = fromList.get(0);
            fromList.remove(0);
            toList.remove(0);
        }
        Collections.reverse(fromList);
        fromList.add(mutualRoot);
        fromList.addAll(toList);

        return fromList;
    }

    public List<String> pathToRoot(Tree tree, String label) {
        if (tree.label.equals(label)) {
            return new ArrayList<>(Collections.singletonList(label));
        }
        for (Tree child : tree.children) {
            List<String> list = pathToRoot(child, label);
            if (!list.isEmpty()) {
                list.add(0, tree.label);
                return list;
            }
        }
        return Collections.emptyList();
    }

    private Tree findSubTree(Tree tree, String toFind) {
        if (tree.label.equals(toFind)) {
            return tree;
        }
        for (Tree current : tree.children) {
            Tree cTree = findSubTree(current, toFind);
            if (cTree != null) {
                return cTree;
            }
        }
        return null;
    }

    private void check(List<String> list, String message) {
        if (list.isEmpty()) {
            throw new UnsupportedOperationException(message);
        }
    }
}
