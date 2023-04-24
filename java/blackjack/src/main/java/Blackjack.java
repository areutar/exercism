public class Blackjack {
    public static final int blackJackScore = 21;
    public static final String STAND = "S";
    public static final String HIT = "H";
    public static final String SPLIT = "P";
    public static final String WIN = "W";

    public int parseCard(String card) {
        return Cards.findCardByName(card).getScore();
    }

    public boolean isBlackjack(String card1, String card2) {
        return parseCard(card1) + parseCard(card2) == blackJackScore;
    }

    public String largeHand(boolean isBlackjack, int dealerScore) {
        if (!isBlackjack) {
            return SPLIT;
        }
        if (dealerScore >= 10) {
            return STAND;
        }
        return WIN;
    }

    public String smallHand(int handScore, int dealerScore) {
        String option = null;
        if (handScore <= 16 && handScore >= 12) {
            if (dealerScore >= 7) {
                option = HIT;
            } else {
                option = STAND;
            }
        }
        if (handScore >= 17) {
            option = STAND;
        }
        if (handScore <= 11) {
            option = HIT;
        }
        return option;
    }

    // FirstTurn returns the semi-optimal decision for the first turn, given the cards of the player and the dealer.
    // This function is already implemented and does not need to be edited. It pulls the other functions together in a
    // complete decision tree for the first turn.
    public String firstTurn(String card1, String card2, String dealerCard) {
        int handScore = parseCard(card1) + parseCard(card2);
        int dealerScore = parseCard(dealerCard);

        if (20 < handScore) {
            return largeHand(isBlackjack(card1, card2), dealerScore);
        } else {
            return smallHand(handScore, dealerScore);
        }
    }
}
