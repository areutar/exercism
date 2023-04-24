class QueenAttackCalculator {
    private final Queen queen1;
    private final Queen queen2;


    public QueenAttackCalculator(Queen queen1, Queen queen2) {
        this.queen1 = queen1;
        this.queen2 = queen2;
        checkBothPositionsValid();
    }

    private void checkBothPositionsValid() {
        if (queen1 == null || queen2 == null) {
            throw new IllegalArgumentException(Queen.YOU_MUST_SUPPLY_VALID_POSITIONS_FOR_BOTH_QUEENS);
        }

        if (queen1.equals(queen2)) {
            throw new IllegalArgumentException(Queen.QUEENS_CANNOT_OCCUPY_THE_SAME_POSITION);
        }
    }

    public boolean canQueensAttackOneAnother() {
        boolean sameRowPosition = queen1.getX() == queen2.getX();
        boolean sameColumnPosition = queen1.getY() == queen2.getY();
        boolean diagonalPosition = Math.abs(queen1.getX() - queen2.getX()) ==
                Math.abs(queen1.getY() - Math.abs(queen2.getY()));
        return sameRowPosition || sameColumnPosition || diagonalPosition;
    }

}
