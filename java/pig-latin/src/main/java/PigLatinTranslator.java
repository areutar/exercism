import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class PigLatinTranslator {
    private final Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
    private final Set<Character> consonants =
            new HashSet<>(Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'));

    public String translate(String input) {
        return Arrays.stream(input.toLowerCase().split(" "))
                .map(this::translateWord)
                .collect(Collectors.joining(" "));
    }

    private String translateWord(String word) {
        String claster;
        String leftover;
        int clasterSize = numberSignsToMove(word);
        claster = word.substring(0, clasterSize);
        leftover = word.substring(clasterSize);

        return leftover.concat(claster).concat("ay");
    }

    private int numberSignsToMove(String word) {
        int num = 0;
        if (word.length() < 2 || isVowels(word.charAt(num))) {
            return 0;
        }

        if (word.startsWith("xr") || word.startsWith("yt")) {
            return 0;
        }

        while (isConsonant(word.charAt(num))) {
            num++;
            if (num == word.length()) return 0;
            if (word.charAt(num) == 'y') break;
        }

        if (word.charAt(num - 1) == 'q' && word.charAt(num) == 'u') num++;
        return num;
    }

    private boolean isConsonant(char c) {
        return consonants.contains(c);
    }

    private boolean isVowels(char c) {
        return vowels.contains(c);
    }
}