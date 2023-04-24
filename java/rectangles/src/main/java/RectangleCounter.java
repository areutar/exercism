import java.util.stream.IntStream;

public class RectangleCounter {
    private String[] inputGrid;

    public int countRectangles(String[] inputGrid) {
        this.inputGrid = inputGrid;
        int height = inputGrid.length;
        if (height < 2) {
            return 0;
        }
        int width = inputGrid[0].length();

        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = i + 1; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    for (int l = k + 1; l < width; l++) {
                        if (checkPlus(inputGrid[i].charAt(k),
                                inputGrid[i].charAt(l),
                                inputGrid[j].charAt(k),
                                inputGrid[j].charAt(l))
                                && checkRow(i, k, l)
                                && checkRow(j, k, l)
                                && checkCol(k, i, j)
                                && checkCol(l, i, j)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private boolean checkRow(int j, int a, int b) {
        return a + 1 == b || IntStream.range(a + 1, b)
                .allMatch(i -> inputGrid[j].charAt(i) == '-' || inputGrid[j].charAt(i) == '+');
    }

    private boolean checkCol(int col, int i, int j) {
        return i + 1 == j || IntStream.range(i + 1, j)
                .allMatch(k -> inputGrid[k].charAt(col) == '|' || inputGrid[k].charAt(col) == '+');
    }

    private boolean checkPlus(char c, char c1, char c2, char c3) {
        return c == '+' && c1 == '+' && c2 == '+' && c3 == '+';
    }
}