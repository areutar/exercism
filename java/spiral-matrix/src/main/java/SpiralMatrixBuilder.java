import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class SpiralMatrixBuilder {
    private final Map<Direction, Direction> clockwise = Map.of(
            Direction.RIGHT, Direction.DOWN,
            Direction.DOWN, Direction.LEFT,
            Direction.LEFT, Direction.UP,
            Direction.UP, Direction.RIGHT
    );
    private final Set<GridPosition> visited = new HashSet<>();

    int[][] buildMatrixOfSize(int size) {
        int[][] matrix = new int[size][size];

        if (size == 0) {
            return matrix;
        }

        int count = 1;
        int countMax = size * size;
        GridPosition currentPosition = new GridPosition(0, 0);
        Direction currentDirection = Direction.RIGHT;

        matrix[0][0] = count;
        visited.add(currentPosition);

        while (count < countMax) {
            count++;
            GridPosition nextPosition = nextPosition(currentPosition, currentDirection);
            if (!isValid(nextPosition, size)) {
                currentDirection = clockwise.get(currentDirection);
                nextPosition = nextPosition(currentPosition, currentDirection);
            }
            currentPosition = nextPosition;
            matrix[currentPosition.x][currentPosition.y] = count;
            visited.add(currentPosition);

        }
        return matrix;
    }

    private boolean isValid(GridPosition position, int size) {
        if (position.x < 0 || position.x >= size
                || position.y < 0 || position.y >= size) {
            return false;
        }
        return !visited.contains(position);
    }

    private GridPosition nextPosition(GridPosition currentPosition, Direction currentDirection) {
        return currentPosition.add(currentDirection.getStep());
    }

}
