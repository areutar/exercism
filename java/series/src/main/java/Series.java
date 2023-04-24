import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Series {
    public static final String SLICE_SIZE_IS_TOO_BIG = "Slice size is too big.";
    public static final String SLICE_SIZE_IS_TOO_SMALL = "Slice size is too small.";
    private final String input;
    private final BiConsumer<Boolean, String> error = (aBoolean, s) -> {
        if (aBoolean) {
            throw new IllegalArgumentException(s);
        }
    };

    public Series(String s) {
        input = s;
    }

    public List<String> slices(int slice) {
        error.accept(slice <= 0, SLICE_SIZE_IS_TOO_SMALL);
        error.accept(slice > input.length(), SLICE_SIZE_IS_TOO_BIG);
        return IntStream.rangeClosed(0, input.length() - slice)
                .mapToObj(i -> input.substring(i, slice + i))
                .collect(Collectors.toList());
    }
}