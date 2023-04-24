public class NWDiagonalLines extends GridLines {

    public NWDiagonalLines(char[][] grid) {
        super(grid);
    }

    @Override
    public void buildStrings() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < i + 1 && j < cols; j++) {
                sb.append(grid[i - j][j]);
            }
            strings.add(sb.toString());
            System.out.println(sb);
            sb.setLength(0);
        }
    }

    @Override
    public Pair buildPair(int lineNumber, int index) {
        return new Pair(index + 1, lineNumber - index + 1);
    }
}
