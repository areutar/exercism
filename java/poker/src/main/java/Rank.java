public enum Rank {
    TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
    SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
    JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

    private final String sign;

    Rank(String sign) {
        this.sign = sign;
    }

    public static Rank findPlayingCardFromSign(String sign) {
        for (Rank rank : Rank.values()) {
            if (rank.getSign().equalsIgnoreCase(sign)) {
                return rank;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getSign() {
        return sign;
    }
}