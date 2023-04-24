import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DiamondPrinter {
    private static String addSpaces(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(value -> " ")
                .collect(Collectors.joining());
    }

    List<String> printToList(char a) {
        if (a > 90 || a < 65) {
            throw new IllegalArgumentException();
        }

        int shift = a - 'A' + 1;
        List<String> diamondList = new ArrayList<>(shift * 2 - 1);
        diamondList.add(addSpaces(shift - 1) + 'A' + addSpaces(shift - 1));
        for (int i = 1; i < shift; i++) {
            diamondList.add(addSpaces(shift - 1 - i) + (char) (65 + i)
                    + addSpaces(2 * i - 1) + (char) (65 + i) + addSpaces(shift - 1 - i));
        }
        for (int i = shift - 2; i > 0; i--) {
            diamondList.add(diamondList.get(i));
        }

        if (shift > 1) {
            diamondList.add(addSpaces(shift - 1) + 'A' + addSpaces(shift - 1));
        }

        return diamondList;
    }

}
