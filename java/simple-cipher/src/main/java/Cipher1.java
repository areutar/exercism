import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Cipher1 {

    String key;

    public Cipher1() {

        this.key = randomKey();

    }

    public Cipher1(String key) {

        if (key == null || key.isEmpty() || !isLegalKey(key)) {

            throw new IllegalArgumentException();

        }

        this.key = key;

    }

    private static int toBase(char i) {

        return (int) i - (int) 'a';

    }

    private static int wrap(int i) {

        return ((i + 26) % 26) + (int) 'a';

    }

    private static boolean isLegalKey(String s) {

        return s.chars().allMatch(Character::isLowerCase);

    }

    private static String randomKey() {

        Random gen = new Random();

        StringBuilder result = new StringBuilder();

        final int min = (int) 'a';

        final int max = (int) 'z';

        for (int i = 0; i < 100; i++) {

            int cur = gen.nextInt(max - min) + min;

            result.append((char) cur);

        }

        return result.toString();

    }

    public String getKey() {

        return key;

    }

    public String encode(String message) {

        return process(message, (m, k) -> m + k);

    }

    public String decode(String message) {

        return process(message, (m, k) -> m - k);

    }

    public String process(String message, IntBinaryOperator f) {

        IntUnaryOperator applyCipher = i -> {

            int messageBase = toBase(message.charAt(i));

            int keyBase = toBase(key.charAt(i % key.length()));

            int result = f.applyAsInt(messageBase, keyBase);

            return wrap(result);

        };

        return IntStream.range(0, message.length())

                .map(applyCipher)

                .mapToObj(c -> "" + (char) c)

                .reduce("", String::concat);

    }

}