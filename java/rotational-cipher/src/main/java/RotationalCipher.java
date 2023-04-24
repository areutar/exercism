import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class RotationalCipher {
    private final Map<Character, Character> cipherMap;

    RotationalCipher(int shiftKey) {
        shiftKey = shiftKey % 26;
        if (shiftKey < 0) {
            shiftKey = 26 + shiftKey;
        }

        cipherMap = new HashMap<>();
        for (int i = 0; i <= 25; i++) {
            cipherMap.put((char) (i + 65), (char) (65 + (i + shiftKey) % 26));
            cipherMap.put((char) (i + 97), (char) (97 + (i + shiftKey) % 26));
        }
    }

    String rotate(String data) {
        return data.chars()
                .mapToObj(i -> (char) i)
                .map(c -> (Character.isLetter(c) && c <= 'z') ? cipherMap.get(c) : c)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
