public class HorizontalLines extends GridLines {

    public HorizontalLines(char[][] grid) {
        super(grid);
    }

    @Override
    public void buildStrings() {
        for (char[] chars : grid) {
            strings.add(String.valueOf(chars));
        }
    }

    @Override
    public Pair buildPair(int lineNumber, int index) {
        return new Pair(index + 1, lineNumber + 1);
    }
}
