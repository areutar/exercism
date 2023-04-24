import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class SgfParsing {
    private static final String PROPERTY_MUST_BE_IN_UPPERCASE = "Property must be in uppercase!";
    private static final String BAD_FORMAT_NODE = "Sgf must be in the format\"(;..)\"";
    private static final String MISSING_PARENTHESES = "Missing parentheses!";
    private static final String BAD_FORMAT_PROPERTY =
            "Properties must be in format \"KEY_UPPERCASE[property1][property2]..\"";
    private static final Pattern propertyPattern =
            Pattern.compile("(?<key>\\w*)\\[(?<property>([^]\\[]|(?<=\\\\)\\[|(?<=\\\\)])+)]");

    public SgfNode parse(String input) throws SgfParsingException {
        checkInput(input);
        Map<String, List<String>> properties;
        SgfNode root = new SgfNode();
        SgfNode current = root;
        boolean hasNode = false;
        int currentPosition = 1;

        while (currentPosition < input.length() - 1) {
            SgfNode child;
            switch (input.charAt(currentPosition)) {
                case ';': {
                    properties = parseProperties(input, ++currentPosition);
                    currentPosition = findNextNodePosition(input, currentPosition);

                    if (hasNode) {
                        child = new SgfNode(properties);
                        current.appendChild(child);
                        current = child;
                    } else {
                        root.setProperties(properties);
                        hasNode = true;
                    }
                    break;
                }
                case '(': {
                    int start = currentPosition;
                    int end = getPosClosingBracket(input, start);
                    currentPosition = end++;
                    String newInput = input.substring(start, end);
                    root.appendChild(parse(newInput));
                    break;
                }
                default: {
                    currentPosition++;
                }
            }
        }
        return root;
    }

    private Map<String, List<String>> parseProperties(String input, int currentPosition) throws SgfParsingException {
        int positionNextNode = findNextNodePosition(input, currentPosition);
        input = input.substring(currentPosition, positionNextNode);
        Matcher matcher = propertyPattern.matcher(input);
        Map<String, List<String>> res = new HashMap<>();
        String key = "";
        List<String> values = new ArrayList<>();

        while (matcher.find()) {
            if (!matcher.group("key").isEmpty()) {
                values = new ArrayList<>();
                key = matcher.group("key");
                checkKey(key);
            }
            values.add(matcher.group("property").replace("\\]", "]"));
            res.put(key, values);
        }
        if (res.isEmpty() && !input.isEmpty()) {
            throw new SgfParsingException(BAD_FORMAT_PROPERTY);
        }
        return res;
    }

    private int findNextNodePosition(String input, int currentPosition) {
        return IntStream.range(currentPosition, input.length())
                .filter(i -> ";()".indexOf(input.charAt(i)) >= 0)
                .findFirst().orElseThrow();
    }

    private void checkKey(String key) throws SgfParsingException {
        if (!key.equals(key.toUpperCase())) {
            throw new SgfParsingException(PROPERTY_MUST_BE_IN_UPPERCASE);
        }
    }

    private void checkInput(String input) throws SgfParsingException {
        if (!input.startsWith("(;") || !input.endsWith(")")) {
            throw new SgfParsingException(BAD_FORMAT_NODE);
        }
    }

    private int getPosClosingBracket(String input, int posOpenBracket) throws SgfParsingException {
        Deque<Integer> brackets = new ArrayDeque<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                brackets.push(i);
            }
            if (input.charAt(i) == ')' && brackets.pop() == posOpenBracket) {
                return i;
            }
        }
        throw new SgfParsingException(MISSING_PARENTHESES);
    }
}
