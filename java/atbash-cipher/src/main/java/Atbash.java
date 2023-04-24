import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Atbash {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public String encode(String phrase) {
        String converted = decode(phrase);
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, converted.length()).forEachOrdered(i -> {
            sb.append(converted.charAt(i));
            if ((i + 1) % 5 == 0) {
                sb.append(" ");
            }
        });
        return sb.toString().strip();
    }

    public String decode(String phrase) {
        return phrase.replaceAll("[\\p{Space}\\p{Punct}]", "")
                .toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .map(c -> {
                    if (Character.isAlphabetic(c)) {
                        return ALPHABET.charAt(ALPHABET.length() - 1 - ALPHABET.indexOf(c));
                    }
                    return c;
                })
                .map(String::valueOf)
                .collect(Collectors.joining());

    }
}