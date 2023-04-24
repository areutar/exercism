import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    private static final Random random = new Random(1000);

    public BigInteger privateKey(BigInteger prime) {
        return getRandom(prime);
    }

    public BigInteger publicKey(BigInteger primeA, BigInteger primeB, BigInteger privateKey) {
        return primeB.modPow(privateKey, primeA);
    }

    public BigInteger secret(BigInteger prime, BigInteger publicKey, BigInteger privateKey) {
        return publicKey.modPow(privateKey, prime);
    }

    private BigInteger getRandom(BigInteger max) {
        BigInteger integer;
        do {
            integer = BigInteger.valueOf(random.nextLong()).mod(max);

        } while (integer.equals(BigInteger.ZERO));
        return integer;
    }
}