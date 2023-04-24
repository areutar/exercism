import java.util.Comparator;
import java.util.stream.LongStream;

class LargestSeriesProductCalculator {
    private static final String SERIES_LENGTH_TOO_LONG =
            "Series length must be less than or equal to the length of the string to search.";
    private static final String STRING_TO_SEARCH_MAY_ONLY_CONTAIN_DIGITS =
            "String to search may only contain digits.";
    private static final String SERIES_LENGTH_MUST_BE_NON_NEGATIVE =
            "Series length must be non-negative.";
    private final String digitsString;

    LargestSeriesProductCalculator(String inputNumber) {
        checkInputStringCorrect(inputNumber);
        digitsString = inputNumber;
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {
        checkNumberOfDigitsCorrect(numberOfDigits);

        return LongStream.rangeClosed(0, digitsString.length() - numberOfDigits)
                .mapToObj(i -> digitsString.substring((int) i, (int) (i + numberOfDigits))
                        .chars()
                        .mapToLong(c -> c - 48)
                        .reduce(1, (left, right) -> left * right))
                .max(Comparator.naturalOrder())
                .orElse(1L);

    }

    private void checkInputStringCorrect(String input) {
        if (!input.matches("[0-9]*")) {
            throw new IllegalArgumentException(
                    STRING_TO_SEARCH_MAY_ONLY_CONTAIN_DIGITS);
        }
    }

    private void checkNumberOfDigitsCorrect(int numberOfDigits) {
        if (numberOfDigits < 0) {
            throw new IllegalArgumentException(
                    SERIES_LENGTH_MUST_BE_NON_NEGATIVE);
        }

        if (numberOfDigits > digitsString.length()) {
            throw new IllegalArgumentException(
                    SERIES_LENGTH_TOO_LONG);
        }
    }
}
