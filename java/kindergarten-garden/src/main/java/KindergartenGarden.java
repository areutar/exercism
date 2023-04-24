import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class KindergartenGarden {
    private final String[] plants = new String[12];

    KindergartenGarden(String garden) {
        Arrays.fill(plants, "");
        garden.lines().forEach(s -> {
            for (int i = 0; i < s.length(); i+=2) {
                plants[i / 2] += s.charAt(i);
                plants[i / 2] += s.charAt(i + 1);
            }
        });
    }

    List<Plant> getPlantsOfStudent(String student) {
        int number = student.toUpperCase().charAt(0) - 'A';
        return getPlantsFromSigns(plants[number]);
    }

    List<Plant> getPlantsFromSigns(String signs) {
        return signs.chars()
                .mapToObj(value -> (char) value)
                .map(Plant::getPlant)
                .collect(Collectors.toList());
    }
}
