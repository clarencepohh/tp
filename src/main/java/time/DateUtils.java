package time;
import java.time.LocalDate;

public class DateUtils {
    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    public static LocalDate getStartOfWeek(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() % NUMBER_OF_DAYS_IN_WEEK);
    }
}
