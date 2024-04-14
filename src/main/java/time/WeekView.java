
package time;

import data.TaskManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ui.UiRenderer.printWeekBody;
import static ui.UiRenderer.printWeekHeader;

public class WeekView extends View {

    /**
     * Constructs a WeekView with the specified start date and date formatter.
     *
     * @param startOfWeek   the start date of the week
     * @param dateFormatter the date formatter for formatting dates
     */
    public WeekView(LocalDate startOfWeek, DateTimeFormatter dateFormatter) {
        super(startOfWeek, dateFormatter);
    }

    /**
     * Prints the week view with tasks from the task manager.
     *
     * @param taskManager the TaskManager object containing tasks
     */
    @Override
    public void printView(TaskManager taskManager) {
        assert startOfView != null : "Start of Weekday missing!";
        LocalDate endOfWeek = startOfView.plusDays(6);
        System.out.println("\nWeek View: " + dateFormatter.format(startOfView) +
                " - " + dateFormatter.format(endOfWeek));

        printWeekHeader(startOfView, dateFormatter, false);
        printWeekBody(startOfView, taskManager);
    }

    /**
     * Moves the view to the next week.
     */
    @Override
    public void next() {
        startOfView = startOfView.plusWeeks(1);
    }

    /**
     * Moves the view to the previous week.
     */
    @Override
    public void previous() {
        startOfView = startOfView.minusWeeks(1);
    }

    /**
     * Retrieves the start date of the week.
     *
     * @return the start date of the week
     */
    public LocalDate getStartOfWeek() {
        return startOfView;
    }

    /**
     * Retrieves the date for a specific day of the week.
     *
     * @param dayOfWeek the day of the week (1 for Monday, 2 for Tuesday, ..., 7 for Sunday)
     * @return the date corresponding to the given day of the week
     */
    public LocalDate getDateForDay(int dayOfWeek) {
        // Assuming dayOfWeek is 1 for Monday, 2 for Tuesday, ..., 7 for Sunday
        // Adjust the value to match ISO-8601 (1 for Monday, ..., 7 for Sunday)
        int currentDayOfWeek = startOfView.getDayOfWeek().getValue();
        int daysDiff = dayOfWeek - currentDayOfWeek;

        // If the dayOfWeek is before the currentDayOfWeek, we need to adjust the daysDiff
        if (daysDiff < 0) {
            daysDiff += 7;
        }
        return startOfView.plusDays(daysDiff);
    }

}
