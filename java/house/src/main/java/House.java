import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class House {
    private final List<Person> persons;
    private static final String DESIGNATION = "This is the %s";
    private static final String ACTION = " that %s the %s";
    public House() {
        persons = Arrays.asList(
                new Person("house that Jack built.", ""),
                new Person("malt", "lay in"),
                new Person("rat", "ate"),
                new Person("cat", "killed"),
                new Person("dog", "worried"),
                new Person("cow with the crumpled horn", "tossed"),
                new Person("maiden all forlorn", "milked"),
                new Person("man all tattered and torn", "kissed"),
                new Person("priest all shaven and shorn", "married"),
                new Person("rooster that crowed in the morn", "woke"),
                new Person("farmer sowing his corn", "kept"),
                new Person("horse and the hound and the horn", "belonged to")
                );
    }

    public String verse(int number) {
        StringBuilder sb = new StringBuilder(
                String.format(DESIGNATION, persons.get(number - 1).name));

        for (int i = number; i > 1; i--) {
            sb.append(String.format(ACTION, persons.get(i - 1).action, persons.get(i - 2).name));
        }

        return sb.toString();
    }

    public String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(this::verse)
                .collect(Collectors.joining("\n"));
    }

    public String sing() {
        return verses(1, persons.size());
    }

    public static class Person {
        private final String name;
        private final String action;

        public Person(String name, String action) {
            this.name = name;
            this.action = action;
        }
    }
}