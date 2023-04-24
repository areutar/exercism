import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookStore {
    private static final int BOOK_COUNT = 5;
    private static final Map<Integer, Double> discounts = Map.of(
            1, 8.0,
            2, 15.2,
            3, 21.6,
            4, 25.6,
            5, 30.0);

    public double calculateBasketCost(List<Integer> books) {
        double min = books.size() * discounts.get(1);
        for (int i = 1; i <= BOOK_COUNT; i++) {
            if (books.stream().distinct().count() < i) {
                break;
            }

            double attempt = discounts.get(i) + calculateBasketCost(removeSeveralDifferentBooks(books, i));
            if (attempt < min) {
                min = attempt;
            }
        }
        return min;
    }

    private List<Integer> removeSeveralDifferentBooks(List<Integer> books, int count) {
        List<Integer> copyList = new ArrayList<>(books);
        List<Integer> uniqueBooks = new ArrayList<>();
        Map<Integer, Integer> frequencies = frequencies(copyList);
        int j = 0;
        for (Map.Entry<Integer, Integer> pair : frequencies.entrySet()) {
            //here we assume that buying unequal books is always more profitable
            //therefore, it is possible and necessary to delete starting from those books, which are many
            if (pair.getValue() > 1) {
                copyList.remove(pair.getKey());
                j++;
            } else {
                uniqueBooks.add(pair.getKey());
            }
            if (j == count) {
                break;
            }
        }
        //then, if necessary, delete all the rest
        for (int i = 0; i < count - j; i++) {
            copyList.remove(uniqueBooks.get(i));
        }
        return copyList;
    }

    private Map<Integer, Integer> frequencies(List<Integer> books) {
        return books.stream()
                .collect(Collectors.toMap(i -> i, i -> 1, Integer::sum));
    }
}