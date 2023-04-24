import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Transpose {
    public String transpose(String toTranspose) {
        List<String> strings = Arrays.stream(toTranspose.split("\n"))
                .map(s -> s.replace(" ", "ы"))
                .collect(Collectors.toList());
        int max = strings.stream().mapToInt(String::length).max().getAsInt();

        return IntStream.range(0, max)
                .mapToObj(value -> strings.stream()
                        .map(s -> value < s.length() ? String.valueOf(s.charAt(value)) : " ")
                        .collect(Collectors.joining()))
                .map(s -> s.replaceFirst("\\s+$", ""))
                .map(s -> s.replace("ы", " "))
                .collect(Collectors.joining("\n"));
    }
}
