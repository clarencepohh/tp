package time;

import data.Task;
import data.TaskManager;
import ui.UiRenderer;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ui.UiRenderer.TODO_ICON_COLOR;
import static ui.UiRenderer.DEADLINE_ICON_COLOR;
import static ui.UiRenderer.EVENT_ICON_COLOR;
import static ui.UiRenderer.ESCAPE_COLOR;

public class MonthView extends View {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private final UiRenderer uiRenderer;

    public MonthView(LocalDate startOfMonth, DateTimeFormatter dateFormatter) {
        super(startOfMonth, dateFormatter);
        this.uiRenderer = new UiRenderer();
    }

    @Override
    public void printView(TaskManager taskManager) {
        logger.log(Level.INFO, "Printing calendar in month view");
        assert startOfView != null : "Start of Month missing!";

        YearMonth yearMonth = YearMonth.from(startOfView);
        LocalDate firstOfMonth = startOfView.withDayOfMonth(1);
        LocalDate currentDate = firstOfMonth.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));

        printMonthHeader(yearMonth);
        uiRenderer.printWeekHeader(startOfView, dateFormatter, true);

        while (currentDate.isBefore(firstOfMonth.plusMonths(1))) {
            printWeek(currentDate, taskManager);
            currentDate = currentDate.plusWeeks(1);
        }
    }

    private void printMonthHeader(YearMonth yearMonth) {
        System.out.println("\nMonth View: " + yearMonth.getMonth() + " " + yearMonth.getYear());
    }

    private void printWeek(LocalDate currentDate, TaskManager taskManager) {
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            printDay(currentDate, startOfView);
            currentDate = currentDate.plusDays(1);
        }
        System.out.println(uiRenderer.VERTICAL_DIVIDER);
        uiRenderer.printSeparator();

        int maxTasks = getMaxTasksForWeek(currentDate.minusDays(7), taskManager);
        printTasksForWeek(currentDate.minusDays(7), maxTasks, taskManager);

        if (maxTasks > 0) {
            uiRenderer.printSeparator();
        }
    }

    private void printDay(LocalDate currentDate, LocalDate startOfMonth) {
        if (currentDate.getMonth().equals(YearMonth.from(startOfMonth).getMonth())) {
            printDayNumber(currentDate);
        } else {
            System.out.print(uiRenderer.EMPTY_TASK_DISPLAY_FORMAT);
        }
    }

    private void printDayNumber(LocalDate currentDate) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d");
        System.out.printf(uiRenderer.ENTRY_FORMAT, dayFormatter.format(currentDate));
    }

    private int getMaxTasksForWeek(LocalDate weekStart, TaskManager taskManager) {
        int maxTasks = 0;
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            LocalDate date = weekStart.plusDays(i);
            maxTasks = Math.max(maxTasks, taskManager.getTasksForDate(date).size());
        }
        return maxTasks;
    }

    private void printTasksForWeek(LocalDate weekStart, int maxTasks, TaskManager taskManager) {
        for (int taskIndex = 0; taskIndex < maxTasks; taskIndex++) {
            for (int dayIndex = 0; dayIndex < NUMBER_OF_DAYS_IN_WEEK; dayIndex++) {
                LocalDate date = weekStart.plusDays(dayIndex);
                List<Task> dayTasks = taskManager.getTasksForDate(date);
                printTaskForDay(dayTasks, taskIndex);
            }
            System.out.println(uiRenderer.VERTICAL_DIVIDER);
        }
    }


    private void printTaskForDay(List<Task> dayTasks, int taskIndex) {
        if (taskIndex < dayTasks.size()) {
            Task task = dayTasks.get(taskIndex);
            printTaskIcon(task);
        } else {
            System.out.print(uiRenderer.EMPTY_TASK_DISPLAY_FORMAT);
        }
    }

    private void printTaskIcon(Task task) {
        String taskIcon = getTaskIcon(task);
        System.out.printf(uiRenderer.ICON_DISPLAY_FORMAT, taskIcon);
    }

    private String getTaskIcon(Task task) {
        String taskIcon;
        switch (task.getTaskType()) {
        case "E":
            taskIcon = getColoredTaskIcon(task, EVENT_ICON_COLOR);
            break;
        case "D":
            taskIcon = getColoredTaskIcon(task, DEADLINE_ICON_COLOR);
            break;
        case "T":
            taskIcon = getColoredTaskIcon(task, TODO_ICON_COLOR);
            break;
        default:
            taskIcon = "";
            break;
        }
        return taskIcon;
    }

    private String getColoredTaskIcon(Task task, String color) {
        String taskIcon;
        if (Objects.equals(task.getMarkedStatusIcon(), "X")) {
            taskIcon = color + "■" + ESCAPE_COLOR;
        } else {
            taskIcon = color + "□" + ESCAPE_COLOR;
        }
        return taskIcon;
    }

    @Override
    public void next() {
        startOfView = startOfView.plusMonths(1);
    }

    @Override
    public void previous() {
        startOfView = startOfView.minusMonths(1);
    }
}
