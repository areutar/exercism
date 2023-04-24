public class PascalsTriangleGenerator {

    public int[][] generateTriangle(int number) {
        int[][] triangle = new int[number][];
        if (number > 0) {
            triangle[0] = new int[]{1};
        }

        if (number > 1) {
            triangle[1] = new int[]{1, 1};
        }

        if (number < 2) {
            return triangle;
        }

        for (int i = 2; i < number; i++) {
            int[] row = new int[triangle[i - 1].length + 1];
            row[0] = 1;
            row[row.length - 1] = 1;
            for (int j = 1; j < row.length - 1; j++) {
                row[j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
            triangle[i] = row;
        }
        return triangle;
    }
}
