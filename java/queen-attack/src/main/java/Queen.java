import java.util.Objects;

public class Queen {
    public static final String QUEEN_POSITION_MUST_HAVE_POSITIVE_ROW =
            "Queen position must have positive row.";
    public static final String QUEEN_POSITION_MUST_HAVE_POSITIVE_COLUMN =
            "Queen position must have positive column.";
    public static final String QUEEN_POSITION_MUST_HAVE_ROW_LESS_THAN_7 =
            "Queen position must have row <= 7.";
    public static final String QUEEN_POSITION_MUST_HAVE_COLUMN_LESS_THAN_7 =
            "Queen position must have column <= 7.";
    public static final String QUEENS_CANNOT_OCCUPY_THE_SAME_POSITION =
            "Queens cannot occupy the same position.";
    public static final String YOU_MUST_SUPPLY_VALID_POSITIONS_FOR_BOTH_QUEENS =
            "You must supply valid positions for both Queens.";

    private final int x;
    private final int y;

    public Queen(int x, int y) {
        this.x = x;
        this.y = y;
        checkPositionsValid();
    }

    private void checkPositionsValid() {
        String exceptionMessage = "";
        if (x < 0) {
            exceptionMessage = QUEEN_POSITION_MUST_HAVE_POSITIVE_ROW;
        }
        if (y < 0) {
            exceptionMessage = QUEEN_POSITION_MUST_HAVE_POSITIVE_COLUMN;
        }
        if (x > 7) {
            exceptionMessage = QUEEN_POSITION_MUST_HAVE_ROW_LESS_THAN_7;
        }
        if (y > 7) {
            exceptionMessage = QUEEN_POSITION_MUST_HAVE_COLUMN_LESS_THAN_7;
        }
        if (x < 0 || y < 0 || x > 7 || y > 7) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queen queen = (Queen) o;
        return x == queen.x && y == queen.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
