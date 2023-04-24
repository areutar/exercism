import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cipher {
    private static final String DEFAULT_KEY = "aaaaaaaaaa";
    private static final int ABC_SIZE = 26;
    private static final char FIRST_LETTER = 'a';
    private final String key;

    public Cipher() {
        this(DEFAULT_KEY);
    }

    public Cipher(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String encode(String plainText) {
        return  IntStream.range(0, plainText.length())
                .mapToObj(i -> "" + plus(plainText.charAt(i), key.charAt(i % key.length())))
                .collect(Collectors.joining());
    }

    public String decode(String cipherText) {
        return IntStream.range(0, cipherText.length())
                .mapToObj(i -> "" + minus(cipherText.charAt(i), key.charAt(i % key.length())))
                .collect(Collectors.joining());
    }

    public char plus(char c1, char c2) {
        return (char) ((c1 - FIRST_LETTER + c2 - FIRST_LETTER) % ABC_SIZE + FIRST_LETTER);
    }

    public char minus(char c1, char c2) {
        return (char) ((c1 - c2 + ABC_SIZE) % ABC_SIZE + FIRST_LETTER);
    }
}



