import java.util.stream.IntStream;

public class NaturalNumber {
    private static final String NEGATIVE_INTEGER_IS_REJECTED
            = "You must supply a natural number (positive integer)";
    private final int number;

    public NaturalNumber(int number) {
        if (number < 1) {
            throw new IllegalArgumentException(NEGATIVE_INTEGER_IS_REJECTED);
        }
        this.number = number;
    }

    public Classification getClassification() {
        int sum = IntStream.rangeClosed(1, number / 2)
                .filter(div -> number % div == 0).sum();
        if (sum == number) {
            return Classification.PERFECT;
        }
        if (sum < number) {
            return Classification.DEFICIENT;
        } else {
            return Classification.ABUNDANT;
        }
    }

}
