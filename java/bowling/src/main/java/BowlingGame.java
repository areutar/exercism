import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    public static final int TOTAL_FRAMES = 10;
    private final List<Frame> frames = new ArrayList<>();
    private Frame currentFrame;

    public int score() {
        BowlingGameExceptionChecker.checkGameIsNotFinished(frames.size(), currentFrame);

        setBonusScoreOnFirstFrames();

        setBonusScoreOnLastFrame();

        return frames.stream().mapToInt(Frame::getScore).sum();
    }

    private void setBonusScoreOnLastFrame() {
        frames.get(TOTAL_FRAMES - 1).setScore(
                frames.get(TOTAL_FRAMES - 1).getRolls().stream()
                        .mapToInt(Integer::valueOf)
                        .sum());
    }

    private void setBonusScoreOnFirstFrames() {
        for (int i = 0; i < frames.size() - 1; i++) {
            Frame frame = frames.get(i);
            List<Integer> bonusRolls = new ArrayList<>();
            bonusRolls.add(frame.getScore());

            Frame frameNext = frames.get(i + 1);
            bonusRolls.addAll(frameNext.getRolls());
            if (i < frames.size() - 2) {
                Frame frameNextTwo = frames.get(i + 2);
                bonusRolls.add(frameNextTwo.getRolls().get(0));
            }

            frame.setScore(bonusRolls.stream()
                    .limit(frame.getGameCase().ordinal() + 1)
                    .mapToInt(Integer::valueOf)
                    .sum());
        }
    }

    public void roll(int pins) {
        BowlingGameExceptionChecker.checkNegativePins(pins);
        BowlingGameExceptionChecker.checkPinsTwoLarge(pins);
        BowlingGameExceptionChecker.checkIfRollAfterGameEnded(frames.size(), currentFrame);
        
        if (addFirstRoll(pins)) return;
        addRoll(pins);
    }

    private void addRoll(int pins) {
        if (currentFrame.isFulfilled()) {
            currentFrame = frames.size() == TOTAL_FRAMES - 1 ?
                    new LastFrame(pins) :
                    new Frame(pins);

            frames.add(currentFrame);
        } else {
            currentFrame.add(pins);
        }
    }

    private boolean addFirstRoll(int pins) {
        if (frames.isEmpty()) {
            currentFrame = new Frame(pins);
            frames.add(currentFrame);
            return true;
        } else {
            currentFrame = frames.get(frames.size() - 1);
        }
        return false;
    }

}