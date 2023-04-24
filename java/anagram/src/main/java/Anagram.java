import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Anagram {
    private final String base;

    public Anagram(String base) {
        this.base = base;
    }

    public List<String> match(List<String> list) {
        return list.stream()
                .filter(s -> isAnagram(s, base))
                .collect(Collectors.toList());
    }

    private boolean isAnagram(String s, String base) {
        if (s.equalsIgnoreCase(base)) {
            return false;
        }
        char[] baseChars = base.toLowerCase().toCharArray();
        char[] sChars = s.toLowerCase().toCharArray();
        Arrays.sort(baseChars);
        Arrays.sort(sChars);
        return Arrays.equals(baseChars, sChars);
    }
}
