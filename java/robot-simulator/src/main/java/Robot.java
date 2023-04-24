import java.util.EnumMap;
import java.util.Map;

public class Robot {
    private static final EnumMap<Orientation, GridPosition> directedStep =
            new EnumMap<>(Map.of(Orientation.NORTH, new GridPosition(0, 1),
                    Orientation.EAST, new GridPosition(1, 0),
                    Orientation.SOUTH, new GridPosition(0, -1),
                    Orientation.WEST, new GridPosition(-1, 0)));
    private GridPosition gridPosition;
    private Orientation orientation;
    private final EnumMap<Command, Move> commands =
            new EnumMap<>(Map.of(Command.RIGHT, this::turnRight,
                    Command.LEFT, this::turnLeft,
                    Command.ADVANCE, this::advance));

    public Robot(GridPosition gridPosition, Orientation orientation) {
        this.gridPosition = gridPosition;
        this.orientation = orientation;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turnRight() {
        orientation = Orientation.values()[(orientation.ordinal() + 1) % 4];
    }

    public void turnLeft() {
        orientation = Orientation.values()[(orientation.ordinal() + 3) % 4];
    }

    public void advance() {
        gridPosition = gridPosition.add(directedStep.get(orientation));
    }

    public void simulate(String input) {
        input.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .map(this::getCommandFromSign)
                .forEach(command -> commands.get(command).move());
    }

    private Command getCommandFromSign(String sign) {
        for (Command command : Command.values()) {
            if (command.name().startsWith(sign)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No such command!");
    }

    enum Command {
        RIGHT, LEFT, ADVANCE
    }

    private interface Move {
        void move();
    }
}