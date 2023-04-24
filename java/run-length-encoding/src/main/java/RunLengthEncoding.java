import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunLengthEncoding {
    private static final Pattern numberPlusSymbol = Pattern.compile("(\\d*)([\\w\\s])");
    private static final Pattern repeatingSymbols = Pattern.compile("(.)\\1*");

    public String encode(String input) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = repeatingSymbols.matcher(input);
        while (matcher.find()) {
            String found = matcher.group();
            if (found.length() > 1) {
                sb.append(found.length());
            }
            sb.append(found.charAt(0));
        }
        return sb.toString();
    }

    public String decode(String input) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = numberPlusSymbol.matcher(input);
        while (matcher.find()) {
            sb.append(matcher.group(2));
            if (!matcher.group(1).isEmpty()) {
                int countSymbols = Integer.parseInt(matcher.group(1));
                sb.append(matcher.group(2).repeat(countSymbols - 1));
            }
        }
        return sb.toString();
    }
}