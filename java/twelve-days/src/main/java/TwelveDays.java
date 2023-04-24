import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TwelveDays {
    private static final List<String> gifts = Arrays.asList(
            "a Partridge in a Pear Tree.\n",
            "two Turtle Doves, and ",
            "three French Hens, ",
            "four Calling Birds, ",
            "five Gold Rings, ",
            "six Geese-a-Laying, ",
            "seven Swans-a-Swimming, ",
            "eight Maids-a-Milking, ",
            "nine Ladies Dancing, ",
            "ten Lords-a-Leaping, ",
            "eleven Pipers Piping, ",
            "twelve Drummers Drumming, ");

    private static final List<String> numbers = Arrays.asList(
            "first",
            "second",
            "third",
            "fourth",
            "fifth",
            "sixth",
            "seventh",
            "eighth",
            "ninth",
            "tenth",
            "eleventh",
            "twelfth");

    private static final List<String> songList;

    static {
        songList = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            String mainPart = String.format(
                    "On the %s day of Christmas my true love gave to me: ", numbers.get(i));
            String enumeration = "";
            for (int j = 0; j < i + 1; j++) {
                enumeration = gifts.get(j).concat(enumeration);
            }
            songList.add(mainPart.concat(enumeration));
        }
    }

    String verse(int verseNumber) {
        return songList.get(verseNumber - 1);
    }

    String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse - 1, endVerse - 1)
                .mapToObj(songList::get)
                .collect(Collectors.joining("\n"));
    }

    String sing() {
        return verses(1, 12);
    }
}
