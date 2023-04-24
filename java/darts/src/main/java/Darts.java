import static java.lang.Math.sqrt;

class Darts {
    private final double x;
    private final double y;

    public Darts(double x, double y) {
        this.x = x;
        this.y = y;
    }

    int score() {
        double distance = sqrt(x * x + y * y);
        if (distance <= 1) {
            return 10;
        }
        if (distance <= 5) {
            return 5;
        }
        if (distance <= 10) {
            return 1;
        }
        return 0;
    }

}
