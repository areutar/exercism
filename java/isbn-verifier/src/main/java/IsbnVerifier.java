import java.util.stream.IntStream;

class IsbnVerifier {

    boolean isValid(String stringToVerify) {
        String isbnString = stringToVerify.replaceAll("-", "").toLowerCase();
        if (!isbnString.matches("[\\d]{10}|[\\d]{9}x")) {
            return false;
        }
        return IntStream.rangeClosed(0, 9)
                .map(i -> {
                    if (i == 9 && isbnString.charAt(i) == 'x') {
                        return 10;
                    } else {
                        return (10 - i) * Character.getNumericValue(isbnString.charAt(i));
                    }
                })
                .sum() % 11 == 0;
    }
}
