import java.util.Optional;

class Twofer {
    String twofer(String name) {
        Optional<String> input = Optional.ofNullable(name);
        return "One for " + input.orElse("you") + ", one for me.";
    }
}
