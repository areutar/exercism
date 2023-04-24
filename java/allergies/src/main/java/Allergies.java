import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Allergies {
    private final List<Allergen> allergies;

    public Allergies(int score) {
        String scoreString = new StringBuilder(Integer.toBinaryString(score % 256)).reverse().toString();
        allergies = IntStream.range(0, scoreString.length())
                .filter(i -> scoreString.charAt(i) == '1')
                .mapToObj(i -> Allergen.values()[i])
                .collect(Collectors.toList());
    }

    public boolean isAllergicTo(Allergen allergen) {
        return allergies.contains(allergen);
    }

    public List<Allergen> getList() {
        return allergies;
    }
}