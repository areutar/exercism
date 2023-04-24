import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BeerSong {
    private static final String PART1 =
            "%s bottles of beer on the wall, %s bottles of beer.\n";
    private static final String PART2 =
            "Take one down and pass it around, %s bottles of beer on the wall.\n\n";
    private static final String NO_BOTTLES_SONG =
            "No more bottles of beer on the wall, no more bottles of beer.\n" +
                    "Go to the store and buy some more, 99 bottles of beer on the wall.\n\n";
    private static final String ONE_BOTTLE_SONG =
            "1 bottle of beer on the wall, 1 bottle of beer.\n" +
                    "Take it down and pass it around, no more bottles of beer on the wall.\n\n";
    private static final String TWO_BOTTLES_SONG =
           "2 bottles of beer on the wall, 2 bottles of beer.\n" +
                   "Take one down and pass it around, 1 bottle of beer on the wall.\n\n";

    public static void main(String[] args) {
        int a = - 1;
        System.out.println((100 + a % 100));
    }

    public String sing(int number, int offset) {
        return IntStream.iterate(number, i -> i - 1)
                .limit(offset)
                .mapToObj(this::bottlesSong)
                .collect(Collectors.joining());
    }

    private String bottlesSong(int number) {
        if (number < 0) {
            return bottlesSong(100 + number % 100);
        }
        switch (number) {
            case 0 : {
                return NO_BOTTLES_SONG;
            }
            case 1 : {
                return ONE_BOTTLE_SONG;
            }
            case 2 : {
                return TWO_BOTTLES_SONG;
            }
            default: {
                return String.format(PART1 + PART2, number, number, number - 1);
            }
        }
    }

    public String singSong() {
        return sing(99, 100);
    }
}
