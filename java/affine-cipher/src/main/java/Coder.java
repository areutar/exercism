import java.util.stream.IntStream;

public abstract class Coder {
    private static final String ERROR_KEY_A_AND_ALPHABET_SIZE_MUST_BE_COPRIME =
            "Error: keyA and alphabet size must be coprime.";
    static final int alphabetLength = 26;
    int a;
    int b;
    int mmi;


    protected Coder(int a, int b) {
        this.a = a;
        this.b = b;
        mmi = getMmi();
    }

    abstract char code(int c);

    abstract String clean(String input);

    String code(String input) {
        return convert(clean(input));
    }

    String convert(String cleaned) {
        return cleaned.chars()
                .mapToObj(c -> Character.isLetter(c) ? code(c) : (char)c)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    int getMmi() {
        return IntStream.range(0, alphabetLength)
                .filter(i -> (a * i - 1) % alphabetLength == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_KEY_A_AND_ALPHABET_SIZE_MUST_BE_COPRIME));
    }
}
