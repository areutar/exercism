import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PhoneNumber {

    private static final List<Function<String, Boolean>> checkings = Arrays.asList(
            s -> s.matches(".*[a-zA-Z]+.*"),
            s -> s.matches("^[^+].*[[^.()\\-]&&\\p{Punct}]+.*"),
            s -> !s.startsWith("1"),
            s -> s.length() > 11,
            s -> s.length() != 10,
            s -> s.charAt(0) == '0',
            s -> s.charAt(0) == '1',
            s -> s.charAt(3) == '0',
            s -> s.charAt(3) == '1'
    );
    private static final List<String> exceptionMessages = Arrays.asList(
            "letters not permitted",
            "punctuations not permitted",
            "11 digits must start with 1",
            "more than 11 digits",
            "incorrect number of digits",
            "area code cannot start with zero",
            "area code cannot start with one",
            "exchange code cannot start with zero",
            "exchange code cannot start with one"
    );
    private final static BiConsumer<Boolean, String> validator = (invalid, message) -> {
        if (invalid) {
            throw new IllegalArgumentException(message);
        }
    };
    private String number;

    public PhoneNumber(String numberString) {

        IntStream.range(0, 2)
                .forEach(i -> validator.accept(checkings.get(i).apply(numberString),
                        exceptionMessages.get(i)));

        number = numberString.replaceAll("\\D", "");

        if (number.length() == 11) {
            validator.accept(checkings.get(2).apply(number), exceptionMessages.get(2));
            number = number.substring(1);
        }

        IntStream.rangeClosed(3, 8)
                .forEach(i -> validator.accept(checkings.get(i).apply(number),
                        exceptionMessages.get(i)));

    }

    public String getNumber() {
        return number;
    }
}