import java.util.ArrayList;
import java.util.List;

class PrimeCalculator {
    private final List<Integer> primes = new ArrayList<>();

    public PrimeCalculator() {
        primes.add(2);
    }

    int nth(int nth) {
        if (nth < 1) {
            throw new IllegalArgumentException();
        }
        int currentNumber = 3;
        while (primes.size() < nth) {
            if (isPrime(currentNumber)) {
                primes.add(currentNumber);
            } else {
                currentNumber++;
            }
        }
        return primes.get(primes.size() - 1);
    }

    private boolean isPrime(int number) {
        return primes.stream()
                .allMatch(integer -> number % integer != 0);
    }

}
