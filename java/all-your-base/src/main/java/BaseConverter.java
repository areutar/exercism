import java.util.*;

public class BaseConverter {
    private static final String BASES_MUST_BE_AT_LEAST_2 = "Bases must be at least 2.";
    private static final String DIGITS_MAY_NOT_BE_NEGATIVE = "Digits may not be negative.";
    private static final String ALL_DIGITS_MUST_BE_STRICTLY_LESS_THAN_THE_BASE =
            "All digits must be strictly less than the base.";
    private final int base;
    private final int[] digits;
    private final int number;

    public BaseConverter(int base, int[] digits) {
        this.base = base;
        this.digits = digits;
        number = getNumber();

        baseCannotBeSmall(base);
        digitsCannotBeNegative(digits);
        digitsCannotBeGreaterThanBase(base, digits);
    }

    public Integer[] convertToBase(int newBase) {
        baseCannotBeSmall(newBase);
        if (number == 0) {
            return new Integer[]{0};
        }

        List<Integer> integers = new ArrayList<>();
        int current = number;
        while (current > 0) {
            int remainder = current % newBase;
            integers.add(remainder);
            current = (current - remainder) / newBase;
        }
        Collections.reverse(integers);
        return integers.toArray(new Integer[0]);
    }

    public int getNumber() {
        if (digits.length == 0) {
            return 0;
        }
        int current = digits[0];
        for (int i = 1; i < digits.length; i++) {
            current = current * base + digits[i];
        }
        return current;
    }

    private void digitsCannotBeGreaterThanBase(int base, int[] digits) {
        if (Arrays.stream(digits)
                .anyMatch(i -> i >= base)) {
            throw new IllegalArgumentException(ALL_DIGITS_MUST_BE_STRICTLY_LESS_THAN_THE_BASE);
        }
    }

    private void digitsCannotBeNegative(int[] digits) {
        if (Arrays.stream(digits)
                .anyMatch(i -> i < 0)) {
            throw new IllegalArgumentException(DIGITS_MAY_NOT_BE_NEGATIVE);
        }
    }

    private void baseCannotBeSmall(int base) {
        if (base < 2) {
            throw new IllegalArgumentException(BASES_MUST_BE_AT_LEAST_2);
        }
    }
}