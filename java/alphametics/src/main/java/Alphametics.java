import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Alphametics {
    private final List<String> leftWords;
    private final String rightWord;
    private final List<Character> allLetters;
    private final StringBuilder sb = new StringBuilder();
    private final Map<Character, Integer> alphMap = new HashMap<>();
    private final Deque<String> permutations = new ArrayDeque<>(List.of(""));
    private final List<String> digits = IntStream.range(0, 10)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());

    public Alphametics(String input) {
        leftWords = Arrays.stream(input.split("\\+|=="))
                .map(String::strip)
                .collect(Collectors.toList());

        allLetters = leftWords.stream()
                .flatMap(s -> s.chars().mapToObj(i -> (char) i))
                .distinct()
                .collect(Collectors.toList());

        rightWord = leftWords.get(leftWords.size() - 1);
        leftWords.remove(leftWords.size() - 1);
    }

    private Optional<String> getPermutation(int k) {
        String current = permutations.pop();
        if (current.length() == k) {
            return Optional.of(current);
        }

        Set<String> remainder = getRemainder(current);
        for (String i : remainder) {
            permutations.push(current + i);
        }
        return Optional.empty();
    }

    private Set<String> getRemainder(String str) {
        return digits.stream()
                .filter(i -> !str.contains(i))
                .collect(Collectors.toSet());
    }

    public Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        try {
            checkInputSize();
            checkNotTooManyLetters();
            checkSumIsNotSmall();
            checkDegenerateSum();
        } catch (Exception e) {
            throw new UnsolvablePuzzleException();
        }

        Set<Character> nonZeroSymbols = leftWords.stream()
                .map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        nonZeroSymbols.add(rightWord.charAt(0));

        int size = allLetters.size();
        while (!permutations.isEmpty()) {
            Optional<String> permutationOpt = getPermutation(size);
            if (permutationOpt.isPresent()) {
                generateMap(permutationOpt.get());
                if (nonZeroSymbols.stream().allMatch(c -> alphMap.get(c) != 0) &&
                        leftWords.stream()
                                .mapToLong(this::generateNumber)
                                .sum() == generateNumber(rightWord)) {
                    return alphMap;
                }
            }
        }
        throw new UnsolvablePuzzleException();
    }

    private void generateMap(String permutation) {
        IntStream.range(0, permutation.length())
                .forEach(j -> alphMap.put(allLetters.get(j),
                        permutation.charAt(j) - '0'));
    }

    private long generateNumber(String str) {
        sb.setLength(0);
        str.chars()
                .mapToObj(c -> (char) c)
                .forEach(c -> sb.append(alphMap.get(c)));
        return Long.parseLong(sb.toString());
    }

    private void checkNotTooManyLetters() throws UnsolvablePuzzleException {
        if (allLetters.size() > 10) {
            throw new UnsolvablePuzzleException();
        }
    }

    private void checkDegenerateSum() throws UnsolvablePuzzleException {
        if (leftWords.size() == 1 && !leftWords.get(0).equals(rightWord)) {
            throw new UnsolvablePuzzleException();
        }
    }

    private void checkInputSize() throws UnsolvablePuzzleException {
        if (leftWords.size() < 2) {
            throw new UnsolvablePuzzleException();
        }
    }

    private void checkSumIsNotSmall() throws UnsolvablePuzzleException {
        int maxStringLength = leftWords.stream()
                .mapToInt(String::length)
                .max().orElse(0);

        if (maxStringLength > rightWord.length()) {
            throw new UnsolvablePuzzleException();
        }
    }
}
