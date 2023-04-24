import java.util.ArrayList;
import java.util.List;

public class Frame {
    protected static final int TOTAL_PINES = 10;
    protected List<Integer> rolls = new ArrayList<>();
    protected int score;
    protected boolean fulfilled;
    protected Cases gameCase;

    public Frame(int pins) {
        rolls.add(pins);
        score = pins;
        fulfilled = pins == TOTAL_PINES;
        gameCase = pins == TOTAL_PINES ? Cases.STRIKE : Cases.SPARE;
    }

    public void add(int pins) {
        rolls.add(pins);
        BowlingGameExceptionChecker.checkIfPinsSumTooBig(rolls.get(0), pins);
        score += pins;
        gameCase = score == TOTAL_PINES ?
                Cases.SPARE :
                Cases.OPEN;
        fulfilled = true;
    }

    public List<Integer> getRolls() {
        return rolls;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Cases getGameCase() {
        return gameCase;
    }

    enum Cases {OPEN, SPARE, STRIKE}
}
