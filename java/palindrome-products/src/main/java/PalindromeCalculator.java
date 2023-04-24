import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PalindromeCalculator {
    private static final String INVALID_INPUT_MIN_MUST_BE_LESS_MAX =
            "invalid input: min must be <= max";

    public SortedMap<Long, List<List<Integer>>> getPalindromeProductsWithFactors(int start, int end) {
        checkArgs(start, end);
        SortedMap<Long, List<List<Integer>>> map = new TreeMap<>();
        for (int i = start; i <= end; i++) {
            for (int j = i; j <= end; j++) {
                long product = (long) i * j;
                if (isPalindrome(String.valueOf(product))) {
                    map.putIfAbsent(product, new ArrayList<>());
                    map.get(product).add(List.of(i, j));
                }
            }
        }
        return map;
    }

    public boolean isPalindrome(String number) {
        final StringBuilder sb = new StringBuilder(number);
        return number.equals(sb.reverse().toString());
    }

    private void checkArgs(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException(INVALID_INPUT_MIN_MUST_BE_LESS_MAX);
        }
    }
}