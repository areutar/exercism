public class VerticalLines extends GridLines {

    public VerticalLines(char[][] grid) {
        super(grid);
    }

    @Override
    public void buildStrings() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                sb.append(grid[j][i]);
            }
            strings.add(sb.toString());
            sb.setLength(0);
        }
    }

    @Override
    public Pair buildPair(int lineNumber, int index) {
        return new Pair(lineNumber + 1, index + 1);
    }
}
