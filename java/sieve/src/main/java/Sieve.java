import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Sieve {
    private final int max;

    Sieve(int max) {
        this.max = max;
    }

    List<Integer> getPrimes() {
        int current = 2;
        boolean[] numbers = new boolean[max + 1];
        Arrays.fill(numbers, true);

        while (current < numbers.length) {
            if (numbers[current]) {
                for (int i = current; i < numbers.length; i++) {
                    int notPrime = i * current;
                    if (notPrime <= max) {
                        numbers[notPrime] = false;
                    } else {
                        break;
                    }
                }
            }
            current++;
        }
        return IntStream.rangeClosed(2, max)
                .filter(i -> numbers[i])
                .boxed()
                .collect(Collectors.toList());
    }
}
