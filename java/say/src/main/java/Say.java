import java.util.Arrays;
import java.util.List;

public class Say {
    public static final List<String> dozens = Arrays.asList(
            "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety");
    private static final List<String> firstNumerals = Arrays.asList(
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen ", "seventeen", "eighteen", "nineteen");
    private static final String ZERO = "zero";
    private static final List<String> bigNumbersSayings = Arrays.asList(
            " hundred ", " thousand ", " million ", " billion ");
    private static final List<Long> bigNumbers = Arrays.asList(100L, 1000L, 1000_000L, 1000_000_000L);

    public String say(long number) {
        checkNumberIsValid(number);
        if (number == 0) {
            return ZERO;
        }
        StringBuilder sayingString = new StringBuilder();

        long quotient;
        long remainder = number;
        for (int i = bigNumbers.size() - 1; i > 0; i--) {
            quotient = remainder / bigNumbers.get(i);
            if (quotient != 0) {
                sayingString.append(sayLessThousand(quotient)).append(bigNumbersSayings.get(i));

            }
            remainder = remainder - quotient * bigNumbers.get(i);
        }
        return (sayingString + sayLessThousand(remainder)).strip();
    }

    public String sayLessThousand(long number) {
        long curr = number / 100;
        return curr == 0 ?
                sayLessHundred(number) :
                firstNumerals.get((int) curr) + bigNumbersSayings.get(0)
                        + sayLessHundred(number % 100);

    }

    public String sayLessHundred(long number) {
        if (number < 20) {
            return firstNumerals.get((int) number);
        } else {
            int units = (int) (number % 10);
            int tens = (int) ((number - units) / 10);
            return units == 0 ?
                    dozens.get(tens - 2) :
                    dozens.get(tens - 2) + "-" + firstNumerals.get(units);
        }
    }

    private void checkNumberIsValid(long number) {
        if (number < 0 || number >= 999_999_999_999L) {
            throw new IllegalArgumentException();
        }
    }

}