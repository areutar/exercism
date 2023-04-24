class DifferenceOfSquaresCalculator {
    int computeSquareOfSumTo(int input) {
        return input * input * (++input) * (input) / 4;
    }

    int computeSumOfSquaresTo(int input) {
        int sum = 0;
        int square = 1;
        for (int i = 1; i < input + 1; i++) {
            sum += square;
            square += i * 2 + 1;
        }
        return sum;
    }

    int computeDifferenceOfSquares(int input) {
        return computeSquareOfSumTo(input) - computeSumOfSquaresTo(input);
    }

}
