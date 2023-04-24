import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Poker {
    private final List<Hand> hands;

    public Poker(List<String> hands) {
        this.hands = hands.stream()
                .map(Hand::new).collect(Collectors.toList());
    }

    public List<String> getBestHands() {
        Hand bestHand = Collections.max(hands);
        return hands.stream()
                .filter(hand -> hand.equals(bestHand))
                .map(Hand::getHandString)
                .collect(Collectors.toList());
    }
}