import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class PrimeFactorsCalculator {
    public List<Long> calculatePrimeFactorsOf(long l) {
        long factorMax = l;
        List<Long> result = new ArrayList<>();

        for (long i = 2; i <= factorMax; i++) {
            while (factorMax % i == 0 && isPrime(i)) {
                factorMax /= i;
                result.add(i);
            }
        }
        return result;
    }

    private boolean isPrime(long l) {
        return LongStream.rangeClosed(2, (long) Math.sqrt(l))
                .allMatch(value -> l % value != 0);
    }

}