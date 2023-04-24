import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Matrix {
    private final int rowNumber;
    private final int columnNumber;
    private final int[][] matrix ;

    Matrix(String matrixAsString) {
        List<String> rowList = matrixAsString.lines().collect(Collectors.toList());
        this.rowNumber = rowList.size();
        this.columnNumber = rowNumber > 0 ? rowList.get(0).split(" ").length : 0;
        String[][] stringMatrix = new String[rowNumber][columnNumber];
        for (int i = 0; i < rowNumber; i++) {
            stringMatrix[i] = rowList.get(i).split(" ");
        }
        matrix = convertStringMatrixToIntMatrix(stringMatrix);
    }

    private int[][] convertStringMatrixToIntMatrix(String[][] stringMatrix) {
        int[][] intMatrix = new int[stringMatrix.length][stringMatrix[0].length];
        for (int i = 0; i < stringMatrix.length; i++) {
            for (int j = 0; j < stringMatrix[0].length; j++) {
                intMatrix[i][j] = Integer.parseInt(stringMatrix[i][j]);
            }
        }
        return intMatrix;
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber - 1];
    }

    int[] getColumn(int columnNumber) {
       return IntStream.range(0, this.rowNumber).map(i -> matrix[i][columnNumber - 1]).toArray();
    }
}
