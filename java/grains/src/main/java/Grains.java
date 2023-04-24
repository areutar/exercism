import java.math.BigInteger;

import static java.math.BigInteger.*;

class Grains {
    private static final String SQUARE_MUST_BE_BETWEEN_1_AND_64 = "square must be between 1 and 64";

    BigInteger grainsOnSquare(final int square) {
        if (square < 1 || square > 64) {
            throw new IllegalArgumentException(SQUARE_MUST_BE_BETWEEN_1_AND_64);
        }
        return TWO.pow(square - 1);
    }

    BigInteger grainsOnBoard() {
        BigInteger powTwo = ONE;
        BigInteger sum = ONE;
        for (int i = 2; i < 65; i++) {
            powTwo = powTwo.multiply(TWO);
            sum = sum.add(powTwo);
        }
        return sum;
    }

}
