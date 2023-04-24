public class SEDiagonalLines extends GridLines {

    public SEDiagonalLines(char[][] grid) {
        super(grid);
    }

    @Override
    public void buildStrings() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < i + 1 && j < rows; j++) {
                sb.append(grid[rows - 1 - j][cols - 1 - i + j]);
            }
            strings.add(sb.toString());
            System.out.println(sb);
            sb.setLength(0);
        }
    }

    @Override
    public Pair buildPair(int lineNumber, int index) {
        return new Pair(cols - 1 - lineNumber + index + 1,
                rows - 1 - index + 1);
    }
}
