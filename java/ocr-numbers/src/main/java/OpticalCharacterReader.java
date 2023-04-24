import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpticalCharacterReader {
    private static final String WRONG_NUMBER_OF_COLS =
            "Number of input columns must be a positive multiple of 3";
    private static final String WRONG_NUMBER_OF_ROWS =
            "Number of input rows must be a positive multiple of 4";
    private static final String QUESTION = "?";

    public String parse(List<String> input) {
        if (input.isEmpty()) {
            return QUESTION;
        }
        checkInput(input);
        StringBuilder sb = new StringBuilder();
        int width = input.get(0).length();
        for (int i = 0; i < input.size(); i += 4) {
            for (int j = 0; j < width; j += 3) {
                List<String> segment = new ArrayList<>();
                for (int k = 0; k < 4; k++) {
                    segment.add(input.get(i + k).substring(j, j + 3));
                }
                sb.append(parseSegment(segment));
            }
            if (input.size() > 4 && i < width - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String parseSegment(List<String> segment) {
        for (int i = 0; i < 10; i++) {
            if (segment.equals(OpticalCharacters.digits.get(i))) {
                return String.valueOf(i);
            }
        }
        return QUESTION;
    }

    private void checkInput(List<String> input) {
        if (input.size() % 4 != 0) {
            throw new IllegalArgumentException(WRONG_NUMBER_OF_ROWS);
        }
        if (input.get(0).length() % 3 != 0) {
            throw new IllegalArgumentException(WRONG_NUMBER_OF_COLS);
        }
    }

    public static class OpticalCharacters {
        private static final List<List<String>> digits = Arrays.asList(
                Arrays.asList(
                        " _ ",
                        "| |",
                        "|_|",
                        "   "),
                Arrays.asList(
                        "   ",
                        "  |",
                        "  |",
                        "   "),
                Arrays.asList(
                        " _ ",
                        " _|",
                        "|_ ",
                        "   "),
                Arrays.asList(
                        " _ ",
                        " _|",
                        " _|",
                        "   "),
                Arrays.asList(
                        "   ",
                        "|_|",
                        "  |",
                        "   "),
                Arrays.asList(
                        " _ ",
                        "|_ ",
                        " _|",
                        "   "),
                Arrays.asList(
                        " _ ",
                        "|_ ",
                        "|_|",
                        "   "),
                Arrays.asList(
                        " _ ",
                        "  |",
                        "  |",
                        "   "),
                Arrays.asList(
                        " _ ",
                        "|_|",
                        "|_|",
                        "   "),
                Arrays.asList(
                        " _ ",
                        "|_|",
                        " _|",
                        "   ")
        );

        private OpticalCharacters() {
            throw new IllegalStateException("Utility class");
        }
    }
}