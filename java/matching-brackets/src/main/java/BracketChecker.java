import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class BracketChecker {
    private static final Map<Character, Character> brackets =
            Map.of('(', ')', '{', '}', '[', ']');
    private final String input;

    public BracketChecker(String input) {
        this.input = input;
    }

    public boolean areBracketsMatchedAndNestedCorrectly() {
        char[] chars = input.toCharArray();
        Deque<Character> stack = new ArrayDeque<>();
        for (Character c : chars) {
            if (brackets.containsKey(c)) {
                stack.push(c);
                continue;
            }
            if (brackets.containsValue(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                Character open = stack.pop();
                if (!brackets.get(open).equals(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}