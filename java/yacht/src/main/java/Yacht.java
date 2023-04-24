import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class Yacht {
    private final List<Integer> roll;
    private final YachtCategory yachtCategory;
    private final BiFunction<Boolean, Integer, Integer> retrieveValidScore =
            (aBoolean, score) -> aBoolean ? score : 0;

    Yacht(int[] dice, YachtCategory yachtCategory) {
        this.yachtCategory = yachtCategory;
        roll = Arrays.stream(dice).boxed().collect(Collectors.toList());

    }

    private static int countOccurrences(List<?> list, Object object) {
        return Collections.frequency(list, object);
//        return (int) list.stream().filter(o -> o.equals(object)).count();
    }

    int score() {

        int distinction = (int) roll.stream().distinct().count();
        int count = countOccurrences(roll, yachtCategory.getValue());
        int sum = roll.stream().mapToInt(Integer::intValue).sum();

        switch (yachtCategory) {
            case YACHT: {
                return retrieveValidScore.apply(distinction == 1, YachtCategory.YACHT.getValue());
            }
            case CHOICE: {
                return sum;
            }
            case BIG_STRAIGHT: {
                return retrieveValidScore.apply(distinction == 5 && !roll.contains(1),
                        YachtCategory.BIG_STRAIGHT.getValue());
            }
            case LITTLE_STRAIGHT: {
                return retrieveValidScore.apply(distinction == 5 && !roll.contains(6),
                        YachtCategory.LITTLE_STRAIGHT.getValue());
            }
            case FOUR_OF_A_KIND: {
                int rolled = roll.get(0).equals(roll.get(1)) ? roll.get(0) : roll.get(5);
                count = countOccurrences(roll, roll.get(0));
                return retrieveValidScore.apply(distinction <= 2 && (count > 3 || count < 2),
                        4 * rolled);
            }
            case FULL_HOUSE: {
                count = countOccurrences(roll, roll.get(0));
                return retrieveValidScore.apply(distinction == 2 && count > 1 && count < 4, sum);
            }

            default: {
                return retrieveValidScore.apply(
                        count > 0, yachtCategory.getValue() * count);
            }
        }
    }
}
