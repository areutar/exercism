import java.util.*;
import java.util.stream.*;

class HighScores {
    private final List<Integer> scores;

    public HighScores(List<Integer> scores) {
        this.scores = scores;
    }

    List<Integer> scores() {
        return scores;
    }

    Integer latest() {
        return scores.get(scores.size() - 1);
    }

    Integer personalBest() {
        return Collections.max(scores);
    }

    List<Integer> personalTopThree() {
        return scores.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
    }
}
