public class PangramChecker {
    public boolean isPangram(String input) {
        String alphabet = "abcdefghijklmnopqastuvwxyz";
        return alphabet.chars()
                .mapToObj(i -> (char) i)
                .filter(c -> input.toLowerCase().indexOf(c) >= 0)
                .count() == 26;
    }
}
