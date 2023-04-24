class Rational {
    private final int numerator;
    private final int denominator;

    Rational(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    private static int gcd(int a, int b) {
        while (a % b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return b;
    }

    int getNumerator() {
        return numerator;
    }

    int getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.getNumerator(), this.getDenominator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }

        Rational other = (Rational) obj;
        return this.getNumerator() == other.getNumerator()
                && this.getDenominator() == other.getDenominator();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + this.getNumerator();
        result = prime * result + this.getDenominator();

        return result;
    }

    public Rational add(Rational rational) {
        return new Rational(numerator * rational.getDenominator()
                + rational.getNumerator() * denominator,
                denominator * rational.getDenominator());
    }

    public Rational subtract(Rational rational) {
        return add(new Rational(-rational.getNumerator(), rational.getDenominator()));
    }

    public Rational multiply(Rational rational) {
        return new Rational(numerator * rational.getNumerator(),
                denominator * rational.getDenominator());
    }

    public Rational divide(Rational rational) {
        return new Rational(numerator * rational.getDenominator(),
                denominator * rational.getNumerator());
    }

    public Rational abs() {
        return new Rational(Math.abs(numerator), Math.abs(denominator));
    }

    public Rational pow(int i) {
        return new Rational((int) Math.pow(numerator, i), ((int) Math.pow(denominator, i)));
    }

    public double exp(double v) {
        return Math.pow(Math.pow(v, 1.0 / denominator), numerator);
    }
}
