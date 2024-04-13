package time;

import data.TaskManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class View {
    protected LocalDate startOfView;
    protected final DateTimeFormatter dateFormatter;

    /**
     * Constructs a View with the specified start date and date formatter.
     *
     * @param startOfView   the start date of the view.
     * @param dateFormatter the date formatter for formatting dates.
     */
    public View(LocalDate startOfView, DateTimeFormatter dateFormatter) {
        this.startOfView = startOfView;
        this.dateFormatter = dateFormatter;
    }

    /**
     * Prints the view with tasks from the task manager.
     *
     * @param taskManager the TaskManager object containing tasks.
     */
    public abstract void printView(TaskManager taskManager);

    /**
     * Moves the view to the next period (e.g., next week or next month).
     */
    public abstract void next();

    /**
     * Moves the view to the previous period (e.g., previous week or previous month).
     */
    public abstract void previous();

    /**
     * Returns the start date of the view.
     *
     * @return the start date of the view
     */
    public LocalDate getStartOfView() {
        return startOfView;
    }

    /**
     * Returns the start date of the month associated with the view.
     *
     * @return the start date of the month
     */
    public LocalDate getStartOfMonth() {
        return startOfView.withDayOfMonth(1);
    }
}
