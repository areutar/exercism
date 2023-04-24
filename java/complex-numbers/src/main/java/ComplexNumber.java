public class ComplexNumber {
    private final double real;
    private final double imag;

    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    public ComplexNumber add(ComplexNumber number) {
        return new ComplexNumber(real + number.getReal(), imag + number.getImag());
    }

    public ComplexNumber minus(ComplexNumber number) {
        return new ComplexNumber(real - number.getReal(), imag - number.getImag());
    }

    public ComplexNumber times(ComplexNumber number) {
        return new ComplexNumber(real * number.getReal() - imag * number.getImag(),
                real * number.getImag() + number.getReal() * imag);
    }

    public ComplexNumber div(ComplexNumber number) {
        return new ComplexNumber((real * number.getReal() + imag * number.getImag())
                / (number.getReal() * number.getReal() + number.getImag() * number.getImag()),
                (imag * number.getReal() - real * number.getImag())
                        / (number.getReal() * number.getReal() + number.getImag() * number.getImag()));
    }

    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imag);
    }

    public ComplexNumber exponentialOf() {
        return new ComplexNumber(Math.exp(real) * Math.cos(imag),
                Math.exp(real) * Math.sin(imag));
    }
}