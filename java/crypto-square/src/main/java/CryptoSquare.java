import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CryptoSquare {
    private final int rows;
    private final int cols;
    private final String text;

    public CryptoSquare(String plainText) {
        text = clean(plainText);
        int length = text.length();
        cols = (int) Math.ceil(Math.sqrt(length));
        rows = length <= cols * (cols - 1) ?
                Math.max(cols - 1, 0) :
                cols;
    }

    private static String clean(String text) {
        return text.replaceAll("\\W", "").toLowerCase();
    }

    public String getCiphertext() {
        List<String> matrix = getMatrix();
        List<String> reverseMatrix = getReversedMatrix(matrix);
        return String.join(" ", reverseMatrix);
    }

    private List<String> getReversedMatrix(List<String> matrix) {
        return IntStream.range(0, cols)
                .mapToObj(i -> matrix.stream()
                        .map(s -> s.length() > i ? String.valueOf(s.charAt(i)) : " ")
                        .collect(Collectors.joining()))
                .collect(Collectors.toList());
    }

    private List<String> getMatrix() {
        List<String> matrix = new ArrayList<>(rows);
        String current = text;
        for (int i = 0; i < rows; i++) {
            int remainder = Math.min(cols, current.length());
            matrix.add(current.substring(0, remainder));
            current = current.substring(remainder);
        }
        return matrix;
    }
}