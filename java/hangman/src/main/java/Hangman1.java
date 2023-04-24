import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.*;
import java.util.stream.Collectors;

public final class Hangman1 {

    private static final String BLANK = "_";

    private static final int totalParts = Part.values().length;

    private Throwable error;

    private Disposable discoverDisposable;

    private Disposable secretDisposable;

    private Status calculateStatus(int numParts, boolean hasLetterMask) {
        if (numParts == totalParts) return Status.LOSS;
        return hasLetterMask ? Status.PLAYING : Status.WIN;
    }

    public Observable<Output> play(Observable<String> secret, Observable<String> guess) {
        List<Output> outputs = new ArrayList<>();
        secret.subscribe(
                new Observer<String>() {

                    public void onSubscribe(Disposable disposable) {
                        secretDisposable = disposable;
                    }

                    public void onNext(String wordToGuess) {
                        error = null;
                        outputs.add(new Output(
                                wordToGuess,
                                BLANK.repeat(wordToGuess.length()),
                                Collections.emptySet(),
                                Collections.emptySet(),
                                Collections.emptyList(),
                                Status.PLAYING));
                    }

                    public void onError(Throwable error) {
                    }

                    public void onComplete() {
                    }
                });

        guess.subscribe(
                new Observer<String>() {

                    public void onSubscribe(Disposable disposable) {
                        discoverDisposable = disposable;
                    }

                    public void onNext(String letter) {
                        Output output = outputs.get(outputs.size() - 1);
                        String secret = output.secret;
                        String discovered = output.discovered;
                        Set<String> guess = new HashSet<>(output.guess);
                        Set<String> misses = new HashSet<>(output.misses);
                        if (guess.contains(letter) || misses.contains(letter))
                            onError(new IllegalArgumentException("Letter " + letter + " was already played"));
                        List<Part> parts = new ArrayList<Part>(output.parts);
                        if (secret.contains(letter)) {
                            String letters = guess.stream().collect(Collectors.joining()) + letter;
                            String regex = "[^" + letters + "]";
                            discovered = secret.replaceAll(regex, BLANK);
                            guess.add(letter);
                        } else {
                            misses.add(letter);
                            parts.add(Part.values()[parts.size()]);
                        }
                        outputs.add(new Output(
                                secret,
                                discovered,
                                guess,
                                misses,
                                parts,
                                calculateStatus(parts.size(), discovered.contains(BLANK))
                        ));
                    }

                    public void onError(Throwable err) {
                        discoverDisposable.dispose();
                        secretDisposable.dispose();
                        error = err;
                    }

                    public void onComplete() {
                    }
                });
        if (error != null) {
            return Observable.error(error);
        }
        return Observable.fromIterable(outputs);
    }
}