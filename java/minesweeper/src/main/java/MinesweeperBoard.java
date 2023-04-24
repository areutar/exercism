import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinesweeperBoard {
    private final String[][] strings;
    private final int rows;
    private final int cols;
    private final Integer[][] shifts = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
    };

    public MinesweeperBoard(List<String> input) {
        this.rows = input.size();
        this.cols = input.size() > 0 ?
                input.get(0).length() :
                0;
        strings = new String[rows][cols];

        IntStream.range(0, rows).forEach(i -> strings[i] = input.get(i).split(""));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (strings[i][j].equals(" ")) {
                    strings[i][j] = String.valueOf(countMines(i, j));
                    if (strings[i][j].equals("0")) {
                        strings[i][j] = " ";
                    }
                }
            }
        }
    }

    private int countMines(int i, int j) {
                return (int) Arrays.stream(shifts)
                        .map(cell -> new Cell(cell[0], cell[1]).sum(new Cell(i, j)))
                        .filter(this::cellIsValid)
                        .filter(cell -> strings[cell.getRow()][cell.getCol()].equals("*"))
                        .count();
    }

    private boolean cellIsValid(Cell cell) {
        return cell.getRow() >= 0 && cell.getRow() < rows
                && cell.getCol() >= 0 && cell.getCol() < cols;
    }

    public List<String> withNumbers() {
        return Arrays.stream(strings)
                .map(s -> String.join("", s))
                .collect(Collectors.toList());
    }
}
