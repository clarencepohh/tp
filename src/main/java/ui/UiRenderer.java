package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.Task;
import data.TaskManager;

public class UiRenderer {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String[] WEEK_DAYS = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};

    private static final int SPACE_COUNT = 10;
    private static final String SINGLE_HORIZONTAL_DIVIDER = "+" + "-".repeat(SPACE_COUNT);
    private static final String END_HORIZONTAL_DIVIDER = "+";
    private static final String VERTICAL_DIVIDER = "|";
    private static final String ENTRY_FORMAT = VERTICAL_DIVIDER + "%-" + SPACE_COUNT + "s";
    private static final String TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + "%-" + SPACE_COUNT + "." + SPACE_COUNT + "s";
    private static final String EMPTY_TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + " ".repeat(SPACE_COUNT);

    private static final int numberOfDaysInWeek = 7;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m"; // Cyan


    /**
     * Prints the header row with the surrounding horizontal dividers.
     * 
     * @param startOfView The date of the start of the week or month view.
     * @param dateFormatter The date formatter to format the date.
     * @param isMonthView A boolean to indicate if the view is a month view.
     */
    public static void printWeekHeader(LocalDate startOfView, DateTimeFormatter dateFormatter, boolean isMonthView) {
        printHorizontalDivider();
        printHeaderRow();
        if (!isMonthView) {
            printDateRow(dateFormatter ,startOfView);
        }
        printHorizontalDivider();
    }

    /**
     * Prints the horizontal divider.
     */
    private static void printHorizontalDivider() {
        for (int i = 0; i < 7; i++) {
            System.out.print(SINGLE_HORIZONTAL_DIVIDER);
        }
        System.out.println(END_HORIZONTAL_DIVIDER);
    }
    
    /**
     * Prints the header row with the days of the week.
     */
    private static void printHeaderRow() {
        for (String day : WEEK_DAYS) {
            System.out.printf(ENTRY_FORMAT, day);
        }
        System.out.println(VERTICAL_DIVIDER);
    }

    /**
     * Prints the body of the week view.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param dateFormatter The date formatter to format the date.
     * @param taskManager The task manager to get the tasks from.
     */
    public static void printWeekBody(LocalDate startOfWeek, DateTimeFormatter dateFormatter, TaskManager taskManager) {
        int maxTasks = getMaxTasks(startOfWeek, taskManager);
        assert maxTasks >= 0 : "maxTasks should be non-negative";
        printWeeksTasks(startOfWeek, maxTasks, taskManager);
        printHorizontalDivider();
    }

    /**
     * Prints the date row with the dates of the week.
     * 
     * @param dateFormatter The date formatter to format the date.
     * @param date The date of the start of the week.
     */
    private static void printDateRow(DateTimeFormatter dateFormatter, LocalDate date) {
        logger.log(Level.INFO, "Printing dates for week starting from " + date);
        for (int i = 0; i < numberOfDaysInWeek; i++) {
            String formattedDate = dateFormatter.format(date);
            System.out.printf(ENTRY_FORMAT, ANSI_CYAN + "\033[2m\033[22m" + dateFormatter.format(date) + ANSI_RESET);

            date = date.plusDays(1);
        }
        System.out.println(VERTICAL_DIVIDER);
    }


    /**
     * Prints the tasks for the week.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param maxTasks The maximum number of tasks of a day in a week.
     * @param taskManager The task manager to get the tasks from.
     */
    public static void printWeeksTasks(LocalDate startOfWeek, int maxTasks, TaskManager taskManager) {
        for (int taskIndex = 0; taskIndex < maxTasks; taskIndex++) {
            for (int dayIndex = 0; dayIndex < numberOfDaysInWeek; dayIndex++) {
                LocalDate currentDate = startOfWeek.plusDays(dayIndex);
                List<Task> dayTasks = taskManager.getTasksForDate(currentDate);
                printTaskForDay(dayTasks, taskIndex);
            }
            System.out.println(VERTICAL_DIVIDER);
        }
    }

    /**
     * Prints the task for the day.
     * 
     * @param dayTasks The list of tasks for the day.
     * @param taskIndex The index of the task to print.
     */
    public static void printTaskForDay(List<Task> dayTasks, int taskIndex) {
        if (taskIndex < dayTasks.size()) {
            Task task = dayTasks.get(taskIndex);
            System.out.printf(TASK_DISPLAY_FORMAT, task.getName());
        } else {
            System.out.print(EMPTY_TASK_DISPLAY_FORMAT);
        }
    }

    /**
     * Returns the maximum number of tasks in a day.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param taskManager The task manager to get the tasks from.
     * @return The maximum number of tasks of a day in a week.
     */
    public static int getMaxTasks(LocalDate startOfWeek, TaskManager taskManager) {
        int maxTasks = 0;
        for (int i = 0; i < numberOfDaysInWeek; i++) {
            LocalDate currentDate = startOfWeek.plusDays(i);
            int tasksSize = taskManager.getTasksForDate(currentDate).size();
            if (tasksSize > maxTasks) {
                maxTasks = tasksSize;
            }
        }
        return maxTasks;
    }

    /**
     * Prints the separator for the week view.
     */
    public static void printSeparator() {
        for (int i = 0; i < 7; i++) {
            System.out.print("+------------");
        }
        System.out.println("+");
    }

    /**
     * Prints the help message for the user.
     */
    public static void printHelp() {
        String horizontalLine = "+-------------------------------------------------------------------------------+";
        String emptyLine = "|                                                                               |";
        System.out.println(horizontalLine);
        System.out.println("|                               Available Commands                              |");
        System.out.println(horizontalLine);
        System.out.println(emptyLine);
        System.out.println("| - 'next': Move to the next week or month view.                                |");
        System.out.println("| - 'prev': Move to the previous week or month view.                            |");
        System.out.println("| - 'update, <day>, <taskIndex>, <newDescription>': Update a task description.  |");
        System.out.println("| - 'add, <day>, <taskType>, <taskDescription>': Add a new task.                |");
        System.out.println("| - 'delete, <day>, <taskIndex>': Delete a task.                                |");
        System.out.println("| - 'mark, <day>, <taskIndex>': Mark a task as complete or not complete.        |");
        System.out.println("| - 'month': Switch to month view.                                              |");
        System.out.println("| - 'week': Switch to week view.                                                |");
        System.out.println("| - 'quit': Exit the calendar application.                                      |");
        System.out.println(emptyLine);
        System.out.println(horizontalLine);
    }


}

