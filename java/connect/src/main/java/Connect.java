import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Connect {
    private static final char PLAYER_O_SIGN = 'O';
    private static final char PLAYER_X_SIGN = 'X';
    private final List<String> board;
    private final Set<Point> grid;
    private final int height;
    private final int width;

    public Connect(String[] input) {
        board = Arrays.stream(input)
                .map(s -> s.replace(" ", ""))
                .collect(Collectors.toList());
        height = board.size();
        width = board.get(0).length();
        grid = IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width).mapToObj(x -> new Point(x, y)))
                .collect(Collectors.toSet());
    }

    public Winner computeWinner() {
        boolean hasOPath = hasPath(PLAYER_O_SIGN, getAllBottomZeros(), point -> point.y == height - 1);
        boolean hasXPath = hasPath(PLAYER_X_SIGN, getAllLeftXs(), point -> point.x == width - 1);

        if (hasXPath) {
            return Winner.PLAYER_X;
        }
        if (hasOPath) {
            return Winner.PLAYER_O;
        }
        return Winner.NONE;
    }

    public boolean hasPath(char playerSign, Set<Point> initialSet, Predicate<Point> isEndPoint) {
        boolean hasPath = false;
        Set<Point> visited = new HashSet<>();
        Point current;
        Deque<Point> toVisit = new ArrayDeque<>(initialSet);

        while (!toVisit.isEmpty()) {
            current = toVisit.pop();
            if (isEndPoint.test(current)) {
                hasPath = true;
                break;
            }
            visited.add(current);
            for (Point point : getNeighbours(current, grid)) {
                if (!visited.contains(point) && getPointSign(point) == playerSign) {
                    toVisit.add(point);
                }
            }
        }
        return hasPath;
    }

    private Set<Point> getAllLeftXs() {
        Set<Point> toVisit = new HashSet<>();
        IntStream.range(0, height)
                .mapToObj(y -> new Point(0, y))
                .filter(point -> getPointSign(point) == PLAYER_X_SIGN)
                .forEach(toVisit::add);
        return toVisit;
    }

    private Set<Point> getAllBottomZeros() {
        Set<Point> toVisit = new HashSet<>();
        IntStream.range(0, width)
                .mapToObj(x -> new Point(x, 0))
                .filter(point -> getPointSign(point) == PLAYER_O_SIGN)
                .forEach(toVisit::add);
        return toVisit;
    }

    private char getPointSign(Point point) {
        return board.get(point.y).charAt(point.x);
    }

    private Set<Point> getNeighbours(Point point, Set<Point> grid) {
        Set<Point> neighbours = new HashSet<>();
        neighbours.add(new Point(point.x + 1, point.y));
//        neighbours.add(new Point(point.x + 1, point.y + 1));
        neighbours.add(new Point(point.x + 1, point.y - 1));
        neighbours.add(new Point(point.x - 1, point.y));
        neighbours.add(new Point(point.x - 1, point.y + 1));
//        neighbours.add(new Point(point.x - 1, point.y - 1));
        neighbours.add(new Point(point.x, point.y - 1));
        neighbours.add(new Point(point.x, point.y + 1));
        neighbours.removeIf(point1 -> !grid.contains(point1));
        return neighbours;
    }
}
