import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hand implements Comparable<Hand> {
    private final HandCategory category;
    private final List<Card> cards = new ArrayList<>(5);
    private final String handString;
    private final List<Rank> kickers = new ArrayList<>();

    public Hand(String handString) {
        this.handString = handString;
        String[] handSigns = handString.split(" ");
        if (handSigns.length != 5) {
            throw new IllegalArgumentException();
        }
        Arrays.stream(handSigns).map(Card::new).forEachOrdered(cards::add);
        cards.sort(Comparator.reverseOrder());
        category = determineCategoryAndKickers();
    }

    private HandCategory determineCategoryAndKickers() {
        Map<Rank, Integer> rankMap = cards.stream()
                .map(Card::getRank).collect(Collectors.toMap(rank -> rank, rank -> 1, Integer::sum));
        Collection<Integer> ints = rankMap.values();

        if (isStrait() || isAceStrait()) {
            return determineStraits();
        }

        if (isFlush()) {
            return determineFlush();
        }

        if (ints.size() == 2) {
            return determineFullAndFour(rankMap);
        }

        if (ints.size() == 3) {
            return determineThreeAndTwoPairs(rankMap);
        }

        if (ints.size() == 4) {
            return determineOnePairs(rankMap);
        }

        if (ints.size() == 5) {
            return determineHigh();
        }

        throw new IllegalArgumentException();
    }

    private HandCategory determineHigh() {
        kickers.addAll(cards.stream()
                .map(Card::getRank)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));
        return HandCategory.HIGH;
    }

    private HandCategory determineOnePairs(Map<Rank, Integer> rankMap) {
        kickers.addAll(getKeysByValue(rankMap, 2));
        List<Card> cardsCopy = new ArrayList<>(cards);
        cardsCopy.removeIf(card -> kickers.contains(card.getRank()));
        cardsCopy.stream()
                .map(Card::getRank)
                .forEachOrdered(kickers::add);
        return HandCategory.ONE_PAIRS;
    }

    private HandCategory determineThreeAndTwoPairs(Map<Rank, Integer> rankMap) {
        if (rankMap.containsValue(3)) {
            kickers.addAll(getKeysByValue(rankMap, 3));
            List<Card> cardsCopy = new ArrayList<>(cards);
            cardsCopy.removeIf(card -> kickers.contains(card.getRank()));
            cardsCopy.stream()
                    .map(Card::getRank)
                    .forEachOrdered(kickers::add);
            return HandCategory.THREE;
        }

        kickers.addAll(getKeysByValue(rankMap, 2));
        kickers.sort(Comparator.reverseOrder());
        kickers.addAll(getKeysByValue(rankMap, 1));
        return HandCategory.TWO_PAIRS;
    }

    private HandCategory determineFullAndFour(Map<Rank, Integer> rankMap) {
        if (rankMap.containsValue(4)) {
            kickers.addAll(getKeysByValue(rankMap, 4));
            kickers.addAll(getKeysByValue(rankMap, 1));
            return HandCategory.FOUR;
        }

        kickers.addAll(getKeysByValue(rankMap, 3));
        kickers.addAll(getKeysByValue(rankMap, 2));
        return HandCategory.FULL_HOUSE;
    }

    private HandCategory determineFlush() {
        kickers.addAll(cards.stream().map(Card::getRank).collect(Collectors.toList()));
        return HandCategory.FLUSH;
    }

    private HandCategory determineStraits() {
        if (isAceStrait()) {
            kickers.add(Rank.FIVE);
        } else {
            kickers.add(cards.stream().map(Card::getRank).max(Comparator.naturalOrder()).get());
        }

        if (isFlush()) {
            return HandCategory.STRAIT_FLUSH;
        }
        return HandCategory.STRAIT;
    }

    private boolean isStrait() {
        return IntStream.range(0, 4)
                .allMatch(i -> cards.get(i).getRank().ordinal() ==
                        cards.get(i + 1).getRank().ordinal() + 1);
    }

    private boolean isAceStrait() {
        Set<Rank> rankSet = cards.stream().map(Card::getRank).collect(Collectors.toSet());
        Set<Rank> aceSet = Set.of(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE);
        return rankSet.containsAll(aceSet);
    }

    private boolean isFlush() {
        return cards.stream()
                .allMatch(card -> card.getSuit().equals(
                        cards.stream()
                                .findFirst().get().getSuit()));
    }

    private <K, V> List<K> getKeysByValue(Map<K, V> maps, V value) {
        List<K> list = new ArrayList<>();
        for (Map.Entry<K, V> pair : maps.entrySet()) {
            if (pair.getValue().equals(value)) {
                list.add(pair.getKey());
            }
        }
        return list;
    }

    public String getHandString() {
        return handString;
    }

    public List<Rank> getKickers() {
        return kickers;
    }

    @Override
    public int compareTo(Hand o) {
        if (category != o.category) {
            return o.category.ordinal() - category.ordinal();
        }
        for (int i = 0; i < kickers.size(); i++) {
            if (kickers.get(i) != o.getKickers().get(i)) {
                return kickers.get(i).ordinal() - o.getKickers().get(i).ordinal();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "category=" + category +
                ", cards=" + cards +
                ", handString='" + handString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return category == hand.category && kickers.equals(hand.kickers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, kickers);
    }

    public enum HandCategory {
        STRAIT_FLUSH, FOUR, FULL_HOUSE, FLUSH, STRAIT, THREE, TWO_PAIRS, ONE_PAIRS, HIGH
    }
}
