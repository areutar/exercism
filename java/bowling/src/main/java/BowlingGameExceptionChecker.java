public class BowlingGameExceptionChecker {
    public static String CANNOT_ROLL_AFTER_GAME_IS_OVER =
            "Cannot roll after game is over";
    public static String SCORE_CANNOT_BE_TAKEN_UNTIL_THE_END_OF_THE_GAME =
            "Score cannot be taken until the end of the game";
    public static String PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE =
            "Pin count exceeds pins on the lane";
    public static String NEGATIVE_ROLL_IS_INVALID =
            "Negative roll is invalid";

    private BowlingGameExceptionChecker() {
    }

    public static void checkNegativePins(int pins) {
        if (pins < 0) {
            throw new IllegalStateException(NEGATIVE_ROLL_IS_INVALID);
        }
    }

    public static void checkPinsTwoLarge(int pins) {
        if (pins > Frame.TOTAL_PINES) {
            throw new IllegalStateException(PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE);
        }
    }

    public static void checkIfRollAfterGameEnded(int size, Frame frame) {
        if (size == BowlingGame.TOTAL_FRAMES && frame.isFulfilled()) {
            throw new IllegalStateException(BowlingGameExceptionChecker.CANNOT_ROLL_AFTER_GAME_IS_OVER);
        }
    }

    public static void checkIfPinsSumTooBig(int pins, int pinsNext) {
        if (pins + pinsNext > Frame.TOTAL_PINES) {
            throw new IllegalStateException(PIN_COUNT_EXCEEDS_PINS_ON_THE_LANE);
        }    }

    public static void checkGameIsNotFinished(int size, Frame frame) {
        if (size < BowlingGame.TOTAL_FRAMES || frame == null || !frame.isFulfilled()) {
            throw new IllegalStateException(
                    BowlingGameExceptionChecker.SCORE_CANNOT_BE_TAKEN_UNTIL_THE_END_OF_THE_GAME);
        }
    }

}
