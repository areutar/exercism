import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class HandshakeCalculator {
    private static final int numberSignals = Signal.values().length;

    List<Signal> calculateHandshake(int input) {
        String binaryString = Integer.toBinaryString(input);
        int length = binaryString.length();

        List<Signal> signals =
                IntStream.range(0, Math.min(numberSignals, length))
                        .filter(i -> binaryString.charAt(length - 1 - i) == '1')
                        .mapToObj(i -> Signal.values()[i]).collect(Collectors.toList());

        if (binaryString.length() > numberSignals
                && binaryString.charAt(length - 1 - numberSignals) == '1') {
            Collections.reverse(signals);
        }
        return signals;
    }
}
