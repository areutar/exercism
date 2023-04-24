import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrimeFactorsCalculatorTest {

    private PrimeFactorsCalculator primeFactorsCalculator;

    @Before
    public void setUp() {
        primeFactorsCalculator = new PrimeFactorsCalculator();
    }

    @Test
    public void testNoFactors() {
        assertEquals(Collections.emptyList(), primeFactorsCalculator.calculatePrimeFactorsOf(1L));
    }

    
    @Test
    public void testPrimeNumber() {
        assertEquals(Collections.singletonList(2L), primeFactorsCalculator.calculatePrimeFactorsOf(2L));
    }

    
    @Test
    public void testSquareOfAPrime() {
        assertEquals(Arrays.asList(3L, 3L), primeFactorsCalculator.calculatePrimeFactorsOf(9L));
    }

    
    @Test
    public void testCubeOfAPrime() {
        assertEquals(Arrays.asList(2L, 2L, 2L), primeFactorsCalculator.calculatePrimeFactorsOf(8L));
    }

    
    @Test
    public void testProductOfPrimesAndNonPrimes() {
        assertEquals(Arrays.asList(2L, 2L, 3L), primeFactorsCalculator.calculatePrimeFactorsOf(12L));
    }

    
    @Test
    public void testProductOfPrimes() {
//        List<Long> list = primeFactorsCalculator.getPrimes(93_819_012_551L);
//        List<Long> list = primeFactorsCalculator.getPrimes(2_147_483_647L);
        Long l = Long.valueOf((Integer.MAX_VALUE) * (25));
        System.out.println(Long.MAX_VALUE);
        System.out.println(l);
        assertEquals(Arrays.asList(5L, 17L, 23L, 461L), primeFactorsCalculator.calculatePrimeFactorsOf(901255L));
    }

    
    @Test
    public void testFactorsIncludingALargePrime() {
        assertEquals(Arrays.asList(11L, 9539L, 894119L), primeFactorsCalculator.calculatePrimeFactorsOf(93_819_012_551L));
    }

}
