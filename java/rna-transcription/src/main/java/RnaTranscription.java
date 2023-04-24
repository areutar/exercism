import java.util.Map;
import java.util.stream.Collectors;

class RnaTranscription {
    private static final Map<Character, Character> complements =
            Map.of('G', 'C',
                    'C', 'G',
                    'T', 'A',
                    'A', 'U');

    String transcribe(String dnaStrand) {
        return dnaStrand.chars()
                .mapToObj(i -> (char) i)
                .map(c-> complements.getOrDefault(Character.toUpperCase(c), c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
