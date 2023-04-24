import java.util.Map;
import java.util.Set;

class Scrabble {
    private final String word;
    private static final Map<Set<Character>, Integer> scrabbleMap =
            Map.of(Set.of('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T'), 1,
                    Set.of('D', 'G'), 2,
                    Set.of('B', 'C', 'M', 'P'), 3,
                    Set.of('F', 'H', 'V', 'W', 'Y'), 4,
                    Set.of('K'), 5,
                    Set.of('J', 'X'), 8,
                    Set.of('Q', 'Z'), 10);

    public Scrabble(String word) {
        this.word = word;
    }

    int getScore() {
        return (int) word.chars().mapToLong(this::getCharacterScore).sum();
    }

    private long getCharacterScore(int value) {
        for (var entry : scrabbleMap.entrySet()) {
            if (entry.getKey().contains(Character.toUpperCase((char) value))) {
                return entry.getValue();
            }
        }
        return 0;
    }
}
