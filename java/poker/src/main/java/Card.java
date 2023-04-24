public class Card implements Comparable<Card>{
    private final Rank rank;
    private final Suit suit;

    public Card(String input) {
        try {
            if (input.startsWith("10")) {
                rank = Rank.TEN;
                suit = Suit.findSuitFromSign(input.charAt(2));
            } else {
                rank = Rank.findPlayingCardFromSign(String.valueOf(input.charAt(0)));
                suit = Suit.findSuitFromSign(input.charAt(1));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    @Override
    public int compareTo(Card o) {
        return this.getRank().compareTo(o.getRank());
    }

    public enum Suit {
        SPADES('S'), HEARTS('H'), DIAMONDS('D'), CLUBS('C');

        private final char sign;

        Suit(char sign) {
            this.sign = sign;
        }

        public static Suit findSuitFromSign(char sign) {
            for (Suit suit : Suit.values()) {
                if (suit.getSign() == sign) {
                    return suit;
                }
            }
            throw new IllegalArgumentException();
        }

        public char getSign() {
            return sign;
        }

    }
}