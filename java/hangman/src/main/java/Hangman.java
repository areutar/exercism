import io.reactivex.Observable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hangman {
    private static final int MAX_NUMBER_OF_PARTS = Part.values().length;
    private static final String LETTER_WAS_ALREADY_PLAYED = "Letter %s was already played";
    private static String errString;
    private final Set<String> guess = new HashSet<>();
    private final Set<String> misses = new HashSet<>();
    private final List<Part> parts = new ArrayList<>();
    private String secret;
    private String discovered;
    private Status status = Status.PLAYING;

    public Observable<Output> play(Observable<String> words, Observable<String> letters) {
        errString = "";
        List<Output> outputs = new ArrayList<>();

        words.subscribe(word -> {
            secret = word;
            prepareToTry(secret);
        });

        letters.subscribe(letter -> {
            if (guess.contains(letter) || misses.contains(letter)) {
                errString = String.format(LETTER_WAS_ALREADY_PLAYED, letter);
            }
            if (status != Status.PLAYING) {
                prepareToTry(letter);
            }
            outputs.add(newTryResult(letter));
        });

        if (!errString.isEmpty()) {
            return Observable.error(new IllegalArgumentException(errString));
        }

        if (letters.isEmpty().blockingGet()) {
            return Observable.just(getCurrentOutput());
        }

        return Observable.fromArray(outputs.toArray(new Output[0]));
    }

    private Output getCurrentOutput() {
        return new Output(secret, discovered, guess, misses, parts, status);
    }

    public void prepareToTry(String word) {
        this.secret = word;
        discovered = "_".repeat(word.length());
        guess.clear();
        misses.clear();
        parts.clear();
        status = Status.PLAYING;
    }

    private Output newTryResult(String letter) {
        if (letter != null && secret.contains(letter)) {
            guess.add(letter);
            discovered = replaceLetter(letter, discovered, secret);
            if (secret.equals(discovered)) {
                status = Status.WIN;
            }
        } else {
            int partsCount = getPartsCount();
            parts.add(Part.values()[partsCount]);
            if (partsCount == MAX_NUMBER_OF_PARTS - 1) {
                status = Status.LOSS;
            }
            misses.add(letter);
        }
        return getCurrentOutput();
    }

    private String replaceLetter(String forReplacement, String word, String pattern) {
        return IntStream.range(0, word.length())
                .mapToObj(i -> pattern.charAt(i) == forReplacement.charAt(0) ?
                        forReplacement.charAt(0) :
                        word.charAt(i))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    int getPartsCount() {
        return parts.stream()
                .map(Part::ordinal)
                .max(Comparator.naturalOrder())
                .orElse(-1) + 1;
    }
}