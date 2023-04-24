import java.util.Collections;
import java.util.List;

public class BinarySearch {
    private static final String VALUE_NOT_IN_ARRAY = "Value not in array";
    private final List<Integer> list;

    public BinarySearch(List<Integer> list) {
        this.list = list;
    }

    public Integer indexOf(int i) throws ValueNotFoundException {
        if (!list.contains(i)) {
            throw new ValueNotFoundException(VALUE_NOT_IN_ARRAY);
        }
        return Collections.binarySearch(list, i);
    }
}