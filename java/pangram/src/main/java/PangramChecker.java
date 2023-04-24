public class PangramChecker {
    public boolean isPangram(String input) {
        return input.toLowerCase()
                .replaceAll("\\P{Alpha}", "")
                .chars()
                .mapToObj(i -> (char) i)
                .distinct()
                .count() == 26;
    }
}
