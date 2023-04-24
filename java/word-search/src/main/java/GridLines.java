import java.util.ArrayList;
import java.util.List;

public abstract class GridLines {
    final int rows;
    final int cols ;
    final char[][] grid;
    final List<String> strings = new ArrayList<>();

    public GridLines(char[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = rows > 0 ? grid[0].length : 0;
        buildStrings();
    }

    public List<String> getStrings() {
        return strings;
    }

    public abstract void buildStrings();

    public abstract Pair buildPair(int lineNumber, int index);

    public WordLocation buildWordLocation(Pair pair, int lineNumber) {
        return new WordLocation(buildPair(lineNumber, pair.getX()),
                buildPair(lineNumber, pair.getY()));
    }
}
