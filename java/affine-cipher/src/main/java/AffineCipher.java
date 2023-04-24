public class AffineCipher {
    public String encode(String input, int a, int b) {
        Encoder encoder = new Encoder(a, b);
        return encoder.splitIntoGroups(encoder.code(input));
    }

    public String decode(String input, int a, int b) {
        Decoder decoder = new Decoder(a, b);
        return decoder.code(input);
    }
}