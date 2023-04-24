import java.util.stream.IntStream;

public class Encoder extends Coder {

    public Encoder(int a, int b) {
        super(a, b);
    }

    @Override
    char code(int c) {
        return (char) ((a * (c - 'a') + b) % alphabetLength + 'a');
    }

    @Override
    public String clean(String input) {
        return input.replaceAll("\\W", "").toLowerCase();
    }

    String splitIntoGroups(String input) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, input.length())
                .forEachOrdered(i -> {
                    sb.append(input.charAt(i));
                    if ((i + 1) % 5 == 0) {
                        sb.append(" ");
                    }
                });
        return sb.toString().strip();
    }
}
