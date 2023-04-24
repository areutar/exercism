import java.util.List;
import java.util.stream.Collectors;

public class Satellite {
    private static final String TRAVERSALS_MUST_HAVE_THE_SAME_LENGTH =
            "traversals must have the same length";
    private static final String TRAVERSALS_MUST_HAVE_THE_SAME_ELEMENTS =
            "traversals must have the same elements";
    private static final String TRAVERSALS_MUST_CONTAIN_UNIQUE_ITEMS =
            "traversals must contain unique items";

    public Tree treeFromTraversals(List<Character> preorderInput, List<Character> inorderInput) {
        checkInputs(preorderInput, inorderInput);
        return new Tree(nodeFromTraversals(preorderInput, inorderInput));
    }

    public Node nodeFromTraversals(List<Character> preorderInput, List<Character> inorderInput) {
        if (preorderInput.isEmpty()) {
            return null;
        }
        char rootValue = preorderInput.get(0);
        int indRoot = inorderInput.indexOf(rootValue);

        List<Character> leftInorderNodes = inorderInput.subList(0, indRoot);
        List<Character> leftPreorderNodes = getPreorderNodes(preorderInput, leftInorderNodes);
        List<Character> rightInorderNodes = inorderInput.subList(indRoot + 1, inorderInput.size());
        List<Character> rightPreorderNodes = getPreorderNodes(preorderInput, rightInorderNodes);
        return new Node(rootValue,
                nodeFromTraversals(leftPreorderNodes, leftInorderNodes),
                nodeFromTraversals(rightPreorderNodes, rightInorderNodes));
    }

    private List<Character> getPreorderNodes(List<Character> preorderInput, List<Character> inorderNodes) {
        return preorderInput.stream()
                .filter(inorderNodes::contains)
                .collect(Collectors.toList());
    }

    private void checkInputs(List<Character> list1, List<Character> list2) {
        if (list1.size() != list2.size()) {
            throw new IllegalArgumentException(TRAVERSALS_MUST_HAVE_THE_SAME_LENGTH);
        }
        if (list1.stream().anyMatch(c -> !list2.contains(c))) {
            throw new IllegalArgumentException(TRAVERSALS_MUST_HAVE_THE_SAME_ELEMENTS);
        }
        if (list1.stream().distinct().count() != list1.size()) {
            throw new IllegalArgumentException(TRAVERSALS_MUST_CONTAIN_UNIQUE_ITEMS);
        }
    }
}
