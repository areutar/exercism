import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FoodChain {
    private static final List<String> roles = Arrays.asList(
            "fly", "spider", "bird", "cat", "dog", "goat", "cow", "horse");

    private static final List<String> roleSentences = Arrays.asList(
            "I don't know why she swallowed the fly. Perhaps she'll die.",
            "It wriggled and jiggled and tickled inside her.\n",
            "How absurd to swallow a bird!\n",
            "Imagine that, to swallow a cat!\n",
            "What a hog, to swallow a dog!\n",
            "Just opened her throat and swallowed a goat!\n",
            "I don't know how she swallowed a cow!\n",
            "She's dead, of course!");

    private static final String BASE_PHRASE = "I know an old lady who swallowed a %s.\n";
    private static final String SWALLOW_PHRASE = "She swallowed the %s to catch the %s.\n";
    private static final String SPIDER_APP = " that wriggled and jiggled and tickled inside her";

    private final List<String> lyrics;

    public FoodChain() {
        lyrics = IntStream.rangeClosed(1, 8)
                .mapToObj(this::verse)
                .collect(Collectors.toList());
    }

    public String verse(int number) {
        StringBuilder verse = new StringBuilder(String.format(BASE_PHRASE, roles.get(number - 1)));
        verse.append(roleSentences.get(number - 1));

        if (number == 1 || number == 8) {
            return verse.toString();
        }

        for (int i = number; i >= 2; i--) {
            if (i == 3) {
                verse.append(String.format(
                        SWALLOW_PHRASE, roles.get(i - 1), roles.get(i - 2) + SPIDER_APP));
            } else {
                verse.append(String.format(SWALLOW_PHRASE, roles.get(i - 1), roles.get(i - 2)));
            }
        }
        return verse + roleSentences.get(0);
    }

    public String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(i -> lyrics.get(i - 1))
                .collect(Collectors.joining("\n\n"));
    }
}