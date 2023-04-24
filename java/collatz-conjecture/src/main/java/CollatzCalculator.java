class CollatzCalculator {
    private static final String ONLY_NATURAL_NUMBERS_ARE_ALLOWED = "Only natural numbers are allowed";

    int computeStepCount(int start) {
        if (start <= 0) {
            throw new IllegalArgumentException(ONLY_NATURAL_NUMBERS_ARE_ALLOWED);
        }
        int number = start;
        int count = 0;
        while (number != 1) {
            number = number % 2 == 0 ? number / 2 : number * 3 + 1;
            count++;
        }
        return count;
    }

}
