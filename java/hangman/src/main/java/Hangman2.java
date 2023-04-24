import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Hangman2 {
    String secret = "";
    String discovered = "";
    Set<String> guess = new HashSet<>();
    Set<String> misses = new HashSet<>();
    List<Part> parts = new ArrayList<>();
    Status status = Status.PLAYING;
    String throwString = "";
    ReplaySubject<Output> subject;

    public void emptyAll() {
        secret = "";
        discovered = "";
        guess = new HashSet<>();
        misses = new HashSet<>();
        parts = new ArrayList<>();
        status = Status.PLAYING;
    }

    public void emit() {
        if (!throwString.isEmpty()) {
            subject.onError(new IllegalArgumentException(throwString));
            subject.onComplete();
        }
        Output newOut = new Output(
                secret,
                discovered,
                guess,
                misses,
                parts,
                status);
        subject.onNext(newOut);
    }

    public Observable<Output> play(Observable<String> word, Observable<String> letters) {
        subject = ReplaySubject.create();
        word.subscribe(this::setSecret);
        letters.subscribe(
                this::letter,
                e -> System.err.println(e),
                () -> {
                    subject.onComplete();
                }
        );
        return subject;
    }

    public void setSecret(String secret) {
        this.secret = secret;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < secret.length(); i++) {
            result.append("_");
        }
        discovered = result.toString();
        emit();
    }

    public void letter(String ch) {
        if (guess.contains(ch) || misses.contains(ch)) {
            throwString = "Letter " + ch + " was already played";
        }
        StringBuilder result = new StringBuilder();
        boolean found = false;
        boolean win = true;
        for (int i = 0; i < secret.length(); i++) {
            if (discovered.charAt(i) != '_') {
                result.append(discovered.charAt(i));
                continue;
            }
            if (secret.charAt(i) == ch.charAt(0)) {
                result.append(ch.charAt(0));
                guess.add(ch);
                found = true;
                continue;
            }
            win = false;
            result.append("_");
        }
        if (!found) {
            misses.add(ch);
            if (status != Status.LOSS) {
                parts.add(Part.values()[parts.size()]);
                if (parts.size() > 5) {
                    status = Status.LOSS;
                }
            }
        }
        if (win) {
            status = Status.WIN;
        }
        discovered = result.toString();
        emit();
        if (status == Status.WIN || status == Status.LOSS) {
            emptyAll();
        }
    }
}