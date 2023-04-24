import java.util.Map;

public class RomanNumerals {
    private static final int decimalLimit = 10000;
    private static final Map<Integer, String> convertMap = Map.of(
            1, "I",
            5, "V",
            10, "X",
            50, "L",
            100, "C",
            500, "D",
            1000, "M",
            5000, "",
            decimalLimit, ""
    );
    private final String romanNumeral;

    public RomanNumerals(int input) {
        StringBuilder sb = new StringBuilder();
        int number = input;
        int decimalPlace = decimalLimit;

        while (number > 0) {
            decimalPlace = decimalPlace / 10;
            sb.append(romanianDigit(convertMap.get(decimalPlace),
                    convertMap.get(decimalPlace * 5), convertMap.get(decimalPlace * 10),
                    number / decimalPlace));
            number = number % decimalPlace;

        }
        romanNumeral = sb.toString();
    }

    /**
     * @param begin    the lowest of the range  e.g. 100
     * @param middle   middle of the range  e.g. 500
     * @param end      the highest of the range e.g. 1000
     * @param position the highest digit of a number in the range between
     * @return Roman numerals for multiples of begin less than end
     */
    private static String romanianDigit(String begin, String middle, String end, int position) {

        switch (position) {
            case 1:
            case 2:
            case 3: {
                return begin.repeat(position);
            }
            case 4: {
                return begin.concat(middle);
            }
            case 5: {
                return middle;
            }
            case 6:
            case 7:
            case 8: {
                return middle.concat(begin.repeat(position - 5));
            }
            case 9: {
                return begin.concat(end);
            }
            default: {
                return "";
            }
        }

    }

    public String getRomanNumeral() {
        return romanNumeral;
    }
}