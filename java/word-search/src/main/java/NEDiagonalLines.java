public class NEDiagonalLines extends GridLines {

    public NEDiagonalLines(char[][] grid) {
        super(grid);
    }

    @Override
    public void buildStrings() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < i + 1 && j < rows; j++) {
                sb.append(grid[j][cols - 1 - i + j]);
            }
            strings.add(sb.toString());
            System.out.println(sb);
            sb.setLength(0);
        }
    }

    @Override
    public Pair buildPair(int lineNumber, int index) {
        return new Pair(rows - 1 - lineNumber + index + 1, index + 1);
    }
}
