import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public Map<String, Integer> phrase(String input) {
        Map<String, Integer> wordCounts = new HashMap<>();
        Arrays.stream(input.strip().split("\\s+|,"))
                .map(s -> s.replaceAll("^\\p{Punct}+|\\p{Punct}+$", "").toLowerCase())
                .filter(s -> !s.isEmpty())
                .forEach(s -> wordCounts.merge(s, 1, Integer::sum));
        return wordCounts;
    }
}
