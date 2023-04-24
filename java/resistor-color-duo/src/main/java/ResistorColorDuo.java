import java.util.List;

class ResistorColorDuo {
    private static final List<String> colorsList =
            List.of("black", "brown", "red", "orange", "yellow",
                    "green", "blue", "violet", "grey", "white");

    int value(String[] colors) {
        return colorsList.indexOf(colors[0]) * 10 + colorsList.indexOf(colors[1]);
    }
}
