import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChangeCalculator {
    private static final String NEGATIVE_TOTALS_ARE_NOT_ALLOWED = "Negative totals are not allowed.";
    private static final String CANNOT_BE_REPRESENTED_IN_THE_GIVEN_CURRENCY =
            "The total %d cannot be represented in the given currency.";
    private List<Integer> coins;

    public ChangeCalculator(List<Integer> coins) {
        Collections.sort(coins);
        this.coins = coins;
    }

    public List<Integer> computeMostEfficientChange(int number) {
        List<Integer> processedInput = processInput(number);
        if (processedInput != null) return processedInput;

        List<List<Integer>> changes = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        Set<List<Integer>> visited = new HashSet<>();
        Queue<List<Integer>> toExplore = new ArrayDeque<>();
        toExplore.add(current);
        boolean coinsResized = true;
        int numberOfTerms = 0;

        while (!toExplore.isEmpty()) {
            current = toExplore.remove();
            // if all possible sums with a coin of the maximum size are generated,
            // and the consideration of sums consisting of a larger number of terms also begins,
            // then the largest coin from the set can be removed
            if (!coinsResized && current.size() != numberOfTerms) {
                decreaseAmountOfCoins(coins.get(coins.size() - 1) - 1);
                coinsResized = true;
            }
            numberOfTerms = current.size();
            int coinsSize = coins.size();
            int sum = getSum(current);

            for (int i = 0; i < coinsSize; i++) {
                int c = coins.get(i);

                if (sum + c <= number) {
                    List<Integer> toAdd = addNewSum(current, c);
                    if (!visited.contains(toAdd)) {
                        toExplore.add(toAdd);
                        visited.add(toAdd);
                        if (sum + c == number) {
                            changes.add(toAdd);
                            break;
                        }
                    }
                } else {
                    if (i == coinsSize - 1) {
                        numberOfTerms = current.size();
                        coinsResized = false;
                    }
                }
            }
        }

        return processResult(number, changes);
    }

    private List<Integer> processResult(int number, List<List<Integer>> changes) {
        if (changes.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(CANNOT_BE_REPRESENTED_IN_THE_GIVEN_CURRENCY, number));
        }

        int min = changes.stream()
                .mapToInt(List::size).min().orElse(0);

        return changes.stream()
                .filter(list -> list.size() == min)
                .findFirst()
                .orElse(Collections.emptyList());
    }

    private List<Integer> addNewSum(List<Integer> current, int c) {
        List<Integer> toAdd = new ArrayList<>(current);
        int index = IntStream.range(0, current.size())
                .filter(j -> c <= toAdd.get(j))
                .findFirst().orElse(current.size());
        toAdd.add(index, c);
        return toAdd;
    }

    private List<Integer> processInput(int number) {
        if (number < 0) {
            throw new IllegalArgumentException(NEGATIVE_TOTALS_ARE_NOT_ALLOWED);
        }
        if (number == 0) {
            return Collections.emptyList();
        }
        decreaseAmountOfCoins(number);
        return null;
    }

    private void decreaseAmountOfCoins(int number) {
        coins = coins.stream()
                .filter(i -> i <= number)
                .collect(Collectors.toList());
    }

    private int getSum(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }
}