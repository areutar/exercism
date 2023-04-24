import java.util.stream.IntStream;

class LuhnValidator {

    boolean isValid(String candidate) {
        String cardNumber = new StringBuilder(
                candidate.replaceAll(" ", ""))
                .reverse().toString();

        return cardNumber.matches("\\d{2,}") &&
                IntStream.range(0, cardNumber.length())
                .map(i -> {
                    int currentDigit = cardNumber.charAt(i) - 48;
                    if (i % 2 != 0) {
                        currentDigit = 2 * currentDigit;
                        return currentDigit > 9 ? currentDigit - 9 : currentDigit;
                    } else {
                        return currentDigit;
                    }
                })
                .sum() % 10 == 0;
    }
}
