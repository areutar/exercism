import java.util.stream.IntStream;

class BirdWatcher {
    private final int[] birdsPerDay;

    public BirdWatcher(int[] birdsPerDay) {
        this.birdsPerDay = birdsPerDay.clone();
    }

    public int[] getLastWeek() {
        return birdsPerDay;
    }

    public int getToday() {
        if (birdsPerDay.length == 0) {
            return 0;
        }
        return birdsPerDay[birdsPerDay.length - 1];
    }

    public void incrementTodaysCount() {
        birdsPerDay[birdsPerDay.length - 1]++;
    }

    public boolean hasDayWithoutBirds() {
        for (int j : birdsPerDay) {
            if (j == 0) {
                return true;
            }
        }
        return false;
    }

    public int getCountForFirstDays(int numberOfDays) {
        int sumFirstDays = 0;
        int countDays = Math.min(numberOfDays, birdsPerDay.length);
        for (int i = 0; i < countDays; i++) {
            sumFirstDays += birdsPerDay[i];
        }
        return sumFirstDays;
    }

    public int getBusyDays() {
        return (int) IntStream.range(0, birdsPerDay.length)
                .filter(value -> birdsPerDay[value] >= 5)
                .count();
    }
}
