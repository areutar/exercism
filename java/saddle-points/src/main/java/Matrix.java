import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Matrix {
    private final List<List<Integer>> matrix;

    Matrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        Set<MatrixCoordinate> resultSet = new HashSet<>();
        if (matrix.isEmpty()) {
            return resultSet;
        }

        List<Integer> rowMaxs = matrix.stream().map(Collections::max)
                .collect(Collectors.toList());

        IntStream.range(0, matrix.get(0).size()).forEach(col -> {
            List<Integer> column = getColumn(col);
            IntStream.range(0, matrix.size())
                    .filter(row -> column.get(row) == Collections.min(column)
                            && matrix.get(row).get(col) == rowMaxs.get(row))
                    .forEach(row -> resultSet.add(new MatrixCoordinate(row + 1, col + 1)));
        });
        return resultSet;
    }

    private List<Integer> getColumn(int number) {
        return matrix.stream().map(integers -> integers.get(number)).collect(Collectors.toList());
    }

}
