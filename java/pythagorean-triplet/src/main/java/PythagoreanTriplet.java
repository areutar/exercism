import java.util.ArrayList;
import java.util.List;

public class PythagoreanTriplet extends Triplet {

    public PythagoreanTriplet(int a, int b, int c) {
        super(a, b, c);
    }

    public static Builder makeTripletsList() {
        return new Builder();
    }

    public static final class Builder {
        private int sum;
        private int max;

        public Builder thatSumTo(int sum) {
            this.sum = sum;
            if (max == 0) {
                this.max = sum / 2;
            }
            return this;
        }

        public Builder withFactorsLessThanOrEqualTo(int max) {
            this.max = max;
            return this;
        }

        public List<PythagoreanTriplet> build() {
            List<PythagoreanTriplet> triplets = new ArrayList<>();
            for (int i = 2; i < max; i++) {
                for (int j = i + 1; j < max; j++) {
                    int c = sum - i - j;
                    if (c <= max && i * i + j * j == c * c) {
                        triplets.add(new PythagoreanTriplet(i, j, c));
                        break;
                    }
                }
            }
            return triplets;
        }
    }
}