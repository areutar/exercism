public class Cell {
    private final int row;
    private final int col;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    Cell sum(Cell cell) {
        return new Cell(row + cell.getRow(), col + cell.getCol());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
