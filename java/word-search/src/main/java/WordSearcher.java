import java.util.*;

public class WordSearcher {
    private final Map<String, Optional<WordLocation>> searchMap = new HashMap<>();

    Map<String, Optional<WordLocation>> search(final Set<String> words, final char[][] grid) {
        searchMap.clear();
        if (grid.length == 0) {
            return searchMap;
        }

        GridLines[] gridLines =  {
            new HorizontalLines(grid), new VerticalLines(grid), new NWDiagonalLines(grid),
                new SEDiagonalLines(grid), new NEDiagonalLines(grid), new SWDiagonalLines(grid)
        };

        for (GridLines lines : gridLines) {
            searchWordsInStrings(words, lines);
        }
        return searchMap;
    }

    private void searchWordsInStrings(Set<String> words, GridLines lines) {
        for (String word : words) {
            for (int i = 0; i < lines.getStrings().size(); i++) {
                String string = lines.getStrings().get(i);
                Optional<Pair> pairOptional = findCoordinateSubString(string, word);
                Pair pair;
                searchMap.putIfAbsent(word, Optional.empty());

                if (pairOptional.isPresent()) {
                    pair = pairOptional.get();
                    WordLocation location = lines.buildWordLocation(pair, i);
                    searchMap.put(word, Optional.of(location));
                }
                // при поиске в другом другом направлении ищется инвертированная строка затем..
                pairOptional = findCoordinateSubString(string, reverseString(word));
                if (pairOptional.isPresent()) {
                    pair = pairOptional.get();
                    // .. затем инвертируются найденные координаты
                    pair = Pair.reverse(pair);
                    WordLocation location = lines.buildWordLocation(pair, i);
                    searchMap.put(word, Optional.of(location));
                }
            }
        }
    }

    private Optional<Pair> findCoordinateSubString(String string, String substring) {
        if (string.equals(substring)) {
            return Optional.of(new Pair(0, string.length() - 1));
        }
        if (string.contains(substring)) {
            int x = string.split(substring)[0].length();
            int y = x + substring.length() - 1;
            return Optional.of(new Pair(x, y));
        }
        return Optional.empty();
    }

    private String reverseString(String string) {
        return new StringBuilder(string).reverse().toString();
    }
}
