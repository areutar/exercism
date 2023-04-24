import java.time.DayOfWeek;
import java.time.LocalDate;

public class Meetup {
    private final LocalDate firstDayInMonth;

    public Meetup(int m, int y) {
        firstDayInMonth = LocalDate.of(y, m, 1);
    }

    public LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        int firstGivenDayOfWeekInMonth = firstDayInMonth.getDayOfWeek().getValue() <= dayOfWeek.getValue() ?
                dayOfWeek.getValue() - firstDayInMonth.getDayOfWeek().getValue() + 1 :
                8 - firstDayInMonth.getDayOfWeek().getValue() + dayOfWeek.getValue();
        int dayInMonth = 1;
        switch (schedule) {
            case FIRST: {
                dayInMonth = firstGivenDayOfWeekInMonth;
                break;
            }
            case SECOND: {
                dayInMonth = firstGivenDayOfWeekInMonth + 7;
                break;
            }
            case THIRD: {
                dayInMonth = firstGivenDayOfWeekInMonth + 14;
                break;
            }
            case FOURTH: {
                dayInMonth = firstGivenDayOfWeekInMonth + 21;
                break;
            }
            case LAST: {
                dayInMonth = firstDayInMonth.lengthOfMonth() >= firstGivenDayOfWeekInMonth + 28 ?
                        firstGivenDayOfWeekInMonth + 28 :
                        firstGivenDayOfWeekInMonth + 21;
                break;
            }
            case TEENTH: {
                dayInMonth = 14 + firstGivenDayOfWeekInMonth < 20 ?
                        firstGivenDayOfWeekInMonth + 14 :
                        firstGivenDayOfWeekInMonth + 7;
                break;
            }
        }
        return LocalDate.of(firstDayInMonth.getYear(), firstDayInMonth.getMonth(), dayInMonth);
    }
}