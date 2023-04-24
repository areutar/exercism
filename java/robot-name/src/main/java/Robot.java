import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Robot {
    private static final List<String> robotNames;

    static {
        robotNames = IntStream.rangeClosed(65, 90)
                .mapToObj(value -> String.valueOf((char) value))
                .flatMap(s -> IntStream.rangeClosed(65, 90)
                        .mapToObj(c -> s + (char) c))
                .flatMap(s -> IntStream.rangeClosed(0, 9)
                        .mapToObj(i -> s + i))
                .flatMap(s -> IntStream.rangeClosed(0, 9)
                        .mapToObj(i -> s + i))
                .flatMap(s -> IntStream.rangeClosed(0, 9)
                        .mapToObj(i -> s + i))
                .collect(Collectors.toList());
    }

    private String name;

    public Robot() {
        reset();
    }

    public String getName() {
        return name;
    }

    public void reset() {
        if (!robotNames.isEmpty()) {
            name = robotNames.remove(0);
        }
    }
}