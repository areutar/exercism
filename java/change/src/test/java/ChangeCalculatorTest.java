import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

public class ChangeCalculatorTest {

    @Test
    public void testChangeThatCanBeGivenInASingleCoin() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 5, 10, 25, 100));

        assertEquals(
                singletonList(25),
                ChangeCalculator1.computeMostEfficientChange(25));
    }

    @Test
    public void testChangeThatMustBeGivenInMultipleCoins() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 5, 10, 25, 100));

        assertEquals(
                asList(5, 10),
                ChangeCalculator1.computeMostEfficientChange(15));
    }

    @Test
    // https://en.wikipedia.org/wiki/Change-making_problem#Greedy_method
    public void testLilliputianCurrency() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 4, 15, 20, 50));

        assertEquals(
                asList(4, 4, 15),
                ChangeCalculator1.computeMostEfficientChange(23));
    }

    @Test
    // https://en.wikipedia.org/wiki/Change-making_problem#Greedy_method
    public void testLowerElbonianCurrency() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 5, 10, 21, 25));

        assertEquals(
                asList(21, 21, 21),
                ChangeCalculator1.computeMostEfficientChange(63));
    }

    @Test
//    @Ignore
    public void testLargeAmountOfChange() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 2, 5, 10, 20, 50, 100));

        assertEquals(
                asList(2, 2, 5, 20, 20, 50, 100, 100, 100, 100, 100, 100, 100, 100, 100),
                ChangeCalculator1.computeMostEfficientChange(999));
    }

    @Test
    // https://en.wikipedia.org/wiki/Change-making_problem#Greedy_method
    public void testPossibleChangeWithoutUnitCoinAvailable() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(2, 5, 10, 20, 50));

        assertEquals(
                asList(2, 2, 2, 5, 10),
                ChangeCalculator1.computeMostEfficientChange(21));
    }

    @Test
    // https://en.wikipedia.org/wiki/Change-making_problem#Greedy_method
    public void testAnotherPossibleChangeWithoutUnitCoinAvailable() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(4, 5));

        assertEquals(
                asList(4, 4, 4, 5, 5, 5),
                ChangeCalculator1.computeMostEfficientChange(27));
    }

    @Test
    public void testZeroChange() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 5, 10, 21, 25));

        assertEquals(
                emptyList(),
                ChangeCalculator1.computeMostEfficientChange(0));
    }

    @Test
    public void testChangeLessThanSmallestCoinInCurrencyCannotBeRepresented() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(5, 10));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ChangeCalculator1.computeMostEfficientChange(3))
                .withMessage("The total 3 cannot be represented in the given currency.");
    }

    @Test
    public void testChangeLargerThanAllCoinsInCurrencyThatCannotBeRepresented() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(5, 10));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ChangeCalculator1.computeMostEfficientChange(94))
                .withMessage("The total 94 cannot be represented in the given currency.");
    }

    @Test
    public void testNegativeChangeIsRejected() {
        ChangeCalculator1 ChangeCalculator1 = new ChangeCalculator1(asList(1, 2, 5));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ChangeCalculator1.computeMostEfficientChange(-5))
                .withMessage("Negative totals are not allowed.");
    }

}
