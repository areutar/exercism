enum YachtCategory {

    YACHT(50),
    ONES(1),
    TWOS(2),
    THREES(3),
    FOURS(4),
    FIVES(5),
    SIXES(6),
    FULL_HOUSE(0),
    FOUR_OF_A_KIND(0),
    LITTLE_STRAIGHT(30),
    BIG_STRAIGHT(30),
    CHOICE(0);

    private int value;

    YachtCategory(int value) {
        this.value = value;
    }

    public static YachtCategory findCategoryByValue(int value) {
        for (YachtCategory category : YachtCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        return YACHT;
    }

    public int getValue() {
        return value;
    }
}
