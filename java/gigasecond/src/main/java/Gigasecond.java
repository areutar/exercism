import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {
    private static final int GIGASECOND = 1_000_000_000;
    private final LocalDateTime gigaMoment;

    public Gigasecond(LocalDate moment) {
        this.gigaMoment = moment.atTime(0, 0).plusSeconds(GIGASECOND);
    }

    public Gigasecond(LocalDateTime moment) {
        this.gigaMoment = moment.plusSeconds(GIGASECOND);
    }

    public LocalDateTime getDateTime() {
        return gigaMoment;
    }
}
