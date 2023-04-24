import java.util.Objects;

public class Clock {
    private int hour;
    private int minute;

    public Clock(int hour, int minute) {
        setTime(hour, minute);
    }

    private void setTime(int hour, int minute) {
        this.minute = (minute % 60 + 60) % 60;
        this.hour = ((hour + minute / 60) % 24 + 24) % 24;
        if (minute < 0 && minute % 60 != 0) {
            this.hour = (this.hour + 23) % 24;
        }
    }

    public void add(int minute) {
        setTime(hour, this.minute + minute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clock clock = (Clock) o;
        return hour == clock.hour && minute == clock.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}