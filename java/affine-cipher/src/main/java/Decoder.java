public class Decoder extends Coder{
    public Decoder(int a, int b) {
        super(a, b);
    }

    @Override
    char code(int c) {
        return (char) (mmi * (c - 'a' - b % alphabetLength + alphabetLength) % alphabetLength + 'a');
    }

    @Override
    String clean(String input) {
        return input.replace(" ", "");
    }
}
