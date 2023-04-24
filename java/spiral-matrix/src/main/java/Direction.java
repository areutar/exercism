public enum Direction {
    RIGHT(new GridPosition(0, 1)),
    DOWN(new GridPosition(1, 0)),
    LEFT(new GridPosition(0, -1)),
    UP(new GridPosition(-1, 0));

    private final GridPosition step;

    Direction(GridPosition step) {
        this.step = step;
    }

    public GridPosition getStep() {
        return step;
    }

}