import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.IntStream;

public class GoCounting {
    private static final int[] X_OFFSETS = {0, 1, 0, -1};
    private static final int[] Y_OFFSETS = {-1, 0, 1, 0};
    private static final String NONE = " ";
    private static final String BLACK = "B";
    private static final String WHITE = "W";
    private final String[][] board;
    private final int rowCount;
    private final int colCount;
    private final EnumMap<Player, Set<Point>> territories = new EnumMap<>(Player.class);

    public GoCounting(String input) {
        board = input.lines().map(s -> s.split("")).toArray(String[][]::new);
        rowCount = board.length;
        colCount = board[0].length;
        for (Player player : Player.values()) {
            territories.put(player, new HashSet<>());
        }
        findTerritories();
    }

    private void findTerritories() {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < colCount; x++) {
                points.add(new Point(x, y));
            }
        }
        List<Point> pointsCopy = new ArrayList<>(points);

        for (Point point : points) {
            if (pointsCopy.contains(new Point(point.x, point.y))) {
                Set<Point> territory = getTerritory(point.x, point.y);
                pointsCopy.removeAll(territory);
                if (board[point.y][point.x].equals(NONE)) {
                    if (territory.stream().noneMatch(this::hasBlackNeighbor) &&
                            territory.stream().anyMatch(this::hasWhiteNeighbor)) {
                        territories.get(Player.WHITE).addAll(territory);
                        continue;
                    }
                    if (territory.stream().noneMatch(this::hasWhiteNeighbor) &&
                            territory.stream().anyMatch(this::hasBlackNeighbor)) {
                        territories.get(Player.BLACK).addAll(territory);
                        continue;
                    }
                    territories.get(Player.NONE).addAll(territory);
                }
            }
        }
    }

    private List<Optional<Point>> getListAdjacent(Point point) {
        List<Optional<Point>> adjacentList = new ArrayList<>();
        for (int i = 0; i < X_OFFSETS.length; i++) {
            int adjX = point.x + X_OFFSETS[i];
            int adjY = point.y + Y_OFFSETS[i];
            if (adjX >= 0 && adjX < colCount && adjY >= 0 && adjY < rowCount) {
                adjacentList.add(Optional.of(new Point(adjX, adjY)));
            } else {
                adjacentList.add(Optional.empty());
            }
        }
        return adjacentList;
    }

    public Player getTerritoryOwner(int x, int y) {
        Point point = new Point(x, y);
        checkPointIsValid(point);
        for (Player player : Player.values()) {
            if (territories.get(player).contains(point)) {
                return player;
            }
        }
        return Player.NONE;
    }

    public Set<Point> getTerritory(int x, int y) {
        Point current = new Point(x, y);
        checkPointIsValid(current);
        Set<Point> visited = new HashSet<>();
        if (!board[y][x].equals(NONE)) {
            return visited;
        }

        Queue<Point> toExplore = new ArrayDeque<>();
        toExplore.add(current);
        while (!toExplore.isEmpty()) {
            current = toExplore.remove();
            visited.add(current);
            for (int i = 0; i < 4; i++) {
                Optional<Point> pointOptional = getListAdjacent(current).get(i);
                if (pointOptional.isPresent() &&
                        getSignFromPoint(pointOptional.get()).equals(board[y][x]) &&
                        !visited.contains(pointOptional.get())) {
                    toExplore.add(pointOptional.get());
                }
            }
        }
        return visited;
    }

    public Map<Player, Set<Point>> getTerritories() {
        return territories;
    }

    private void checkPointIsValid(Point point) {
        if (point.x < 0 || point.y < 0 || point.x >= colCount || point.y >= rowCount) {
            throw new IllegalArgumentException("Invalid coordinate");
        }
    }

    private boolean hasWhiteNeighbor(Point point) {
        return IntStream.range(0, 4)
                .mapToObj(i -> getListAdjacent(point).get(i))
                .anyMatch(point1 -> point1.isPresent() && getSignFromPoint(point1.get()).equals(WHITE));
    }

    private boolean hasBlackNeighbor(Point point) {
        return IntStream.range(0, 4)
                .mapToObj(i -> getListAdjacent(point).get(i))
                .anyMatch(point1 -> point1.isPresent() && getSignFromPoint(point1.get()).equals(BLACK));

    }

    private String getSignFromPoint(Point point) {
        checkPointIsValid(point);
        return board[point.y][point.x];
    }
}