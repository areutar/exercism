import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class RailFenceCipher {
    private final int rows;

    public RailFenceCipher(int rows) {
        this.rows = rows;
    }

    public String getEncryptedData(String input) {
        char[][] matrix = getRailMatrix(rows, input);

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < input.length(); j++) {
                if (matrix[k][j] != '_') {
                    sb.append(matrix[k][j]);
                }
            }
        }
        return sb.toString();
    }

    public String getDecryptedData(String input) {
        String asterisks = "*".repeat(input.length());
        char[][] matrix = getRailMatrix(rows, asterisks);

        Queue<Character> charQueue = input.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toCollection(LinkedList::new));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < input.length(); j++) {
                if (matrix[i][j] == '*') {
                    matrix[i][j] = charQueue.remove();
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        int count = 0;
        int inc = 1;
        while (i < input.length()) {
            sb.append(matrix[count][i]);
            i++;
            count += inc;
            if (count == rows - 1 || count == 0) {
                inc = -inc;
            }
        }

        return sb.toString();
    }

    private char[][] getRailMatrix(int rows, String input) {
        char[][] matrix = new char[rows][input.length()];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(matrix[i], '_');
        }

        int i = 0;
        int count = 0;
        int inc = 1;
        while (i < input.length()) {
            matrix[count][i] = input.charAt(i);
            i++;
            count += inc;
            if (count == rows - 1 || count == 0) {
                inc = -inc;
            }
        }
        return matrix;
    }
}