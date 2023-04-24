import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NucleotideCounter {

    private final String nucleotide;
    private final String BASES = "AGCT";

    public NucleotideCounter(String nucleotide) {
        if (!nucleotide.matches("[AGCT]*")) {
            throw new IllegalArgumentException();
        }
        this.nucleotide = nucleotide;
    }

    public Map<Character, Integer> nucleotideCounts() {
//        Map<Character, Integer> basesMap = new HashMap<>();
//        BASES.chars().forEach(value -> basesMap.put((char) value, 0));
//        nucleotide.chars()
//                .mapToObj(c -> (char) c)
//                .forEach(c -> basesMap.merge(c, 1, Integer::sum));
//        return basesMap;

//        return BASES.chars()
//                .mapToObj(i -> (char) i)
//                .collect(Collectors.toMap(
//                        Function.identity(),
//                        character -> nucleotide.split(character.toString(), -1).length - 1
//                ));

        return BASES.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toMap(
                        Function.identity(),
                        character -> nucleotide.split(character.toString(), -1).length - 1
                ));

//        return BASES.chars()
//                .mapToObj(i -> (char) i)
//                .collect(Collectors.toMap(
//                        character -> character,
//                        character -> (int) nucleotide.chars()
//                                .mapToObj(value -> (char) value)
//                                .filter(ch -> ch.equals(character))
//                                .count()
//                ));

    }
}