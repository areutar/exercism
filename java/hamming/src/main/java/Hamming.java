import java.util.stream.IntStream;

/**
 * The type Hamming.
 */
class Hamming {
    /**
     * The Hamming distance.
     */
    public static final String LEFT_STRAND_MUST_NOT_BE_NULL = "left strand must not be null.";
    public static final String LEFT_STRAND_MUST_NOT_BE_EMPTY = "left strand must not be empty.";
    public static final String RIGHT_STRAND_MUST_NOT_BE_NULL = "right strand must not be null.";
    public static final String RIGHT_STRAND_MUST_NOT_BE_EMPTY = "right strand must not be empty.";
    public static final String LEFT_STRAND_AND_RIGHT_STRAND_MUST_BE_OF_EQUAL_LENGTH =
            "leftStrand and rightStrand must be of equal length.";
    private final int hammingDistance;

    /**
     * Instantiates a new Hamming.
     *
     * @param left  the left
     * @param right the right
     */
    public Hamming(String left, String right) {
        checkParametersAreValid(left, right);
        hammingDistance = (int) IntStream.range(0, left.length())
                .filter(i -> left.charAt(i) != right.charAt(i))
                .count();
    }

    /**
     * Check parameters are valid.
     *
     * @param left  the left
     * @param right the right
     */
    private void checkParametersAreValid(String left, String right) {
        if (left == null) {
            throw new NullPointerException(LEFT_STRAND_MUST_NOT_BE_NULL);
        }

        if (right == null) {
            throw new NullPointerException(RIGHT_STRAND_MUST_NOT_BE_NULL);
        }

        if (left.isEmpty() & !right.isEmpty()) {
            throw new IllegalArgumentException(LEFT_STRAND_MUST_NOT_BE_EMPTY);
        }

        if (!left.isEmpty() & right.isEmpty()) {
            throw new IllegalArgumentException(RIGHT_STRAND_MUST_NOT_BE_EMPTY);
        }

        if (left.length() != right.length()) {
            throw new IllegalArgumentException(LEFT_STRAND_AND_RIGHT_STRAND_MUST_BE_OF_EQUAL_LENGTH);
        }
    }

    /**
     * Gets hamming distance.
     *
     * @return the hamming distance
     */
    public int getHammingDistance() {
        return hammingDistance;
    }
}