public class LastFrame extends Frame {

    public LastFrame(int pins) {
        super(pins);
        fulfilled = false;
    }

    @Override
    public void add(int pins) {
        if (rolls.size() == 1 && gameCase != Cases.STRIKE) {
            BowlingGameExceptionChecker.checkIfPinsSumTooBig(rolls.get(0), pins);
        }

        if (rolls.size() == 2 &&
                gameCase.equals(Cases.STRIKE) &&
                rolls.get(1) != TOTAL_PINES) {
            BowlingGameExceptionChecker.checkIfPinsSumTooBig(rolls.get(1), pins);
        }

        rolls.add(pins);

        if (rolls.get(0) + rolls.get(1) < TOTAL_PINES) {
            gameCase = Cases.OPEN;
            fulfilled = true;
            return;
        }

        setFulfilled(getRolls().size() == 3);
    }
}
