import java.util.Arrays;
import java.util.stream.Collectors;

class Acronym {
    private final String phrase;
    Acronym(String phrase) {
        this.phrase = phrase;
    }

    String get() {
        return Arrays.stream(phrase.split("[\\s-_]+"))
                .map(s -> s.charAt(0))
                .map(Character::toUpperCase)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
