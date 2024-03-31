package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.Task;
import data.TaskManager;

public class UiRenderer {

    private static Map<LocalDate, List<List<String>>> allWrappedTaskLines = new HashMap<>();

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String[] WEEK_DAYS = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};

    private static final int SPACE_COUNT = 15;
    private static final String SINGLE_HORIZONTAL_DIVIDER = "+" + "-".repeat(SPACE_COUNT);
    private static final String END_HORIZONTAL_DIVIDER = "+";
    private static final String VERTICAL_DIVIDER = "|";
    private static final String ENTRY_FORMAT = VERTICAL_DIVIDER + "%-" + SPACE_COUNT + "s";
    private static final String TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + "%-" + SPACE_COUNT + "." + SPACE_COUNT + "s";
    private static final String EMPTY_TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + " ".repeat(SPACE_COUNT);

    private static final int numberOfDaysInWeek = 7;

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
        for (int i = 0; i < numberOfDaysInWeek; i++) {
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
     * @param taskManager The task manager to get the tasks from.
     */
    public static void printWeekBody(LocalDate startOfWeek, TaskManager taskManager) {
        printTasksInWeek(startOfWeek, taskManager);
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
            System.out.printf(ENTRY_FORMAT, formattedDate);

            date = date.plusDays(1);
        }
        System.out.println(VERTICAL_DIVIDER);
    }

    /**
     * Prints the tasks in the week.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param taskManager The task manager to get the tasks from.
     */
    public static void printTasksInWeek(LocalDate startOfWeek, TaskManager taskManager) {
        storeWrappedLines(startOfWeek, taskManager);
        int maxNumberOfTasksInDay = getMaxNumberOfTasksInDay(allWrappedTaskLines);
        int maxNumberOfLinesPerTask = getMaxNumberOfLinesPerTask(allWrappedTaskLines);
        printTasksInGrid(startOfWeek, maxNumberOfTasksInDay, maxNumberOfLinesPerTask);
    }
    
    /**
     * Returns the maximum number of tasks in a day.
     * 
     * @param allWrappedTaskLines The map of wrapped task lines.
     * @return The maximum number of tasks in a day.
     */
    private static int getMaxNumberOfTasksInDay(Map<LocalDate, List<List<String>>> allWrappedTaskLines) {
        int maxNumberOfTasksInDay = 0;
        
        for (List<List<String>> dayTasks : allWrappedTaskLines.values()) {
            if (dayTasks.size() > maxNumberOfTasksInDay) {
                maxNumberOfTasksInDay = dayTasks.size();
            }
        }
        
        return maxNumberOfTasksInDay;
    }

    /**
     * Returns the maximum number of lines per task.
     * 
     * @param allWrappedTaskLines The map of wrapped task lines.
     * @return The maximum number of lines per task.
     */
    private static int getMaxNumberOfLinesPerTask(Map<LocalDate, List<List<String>>> allWrappedTaskLines) {
        int maxNumberOfLinesPerTask = 0;
        
        for (List<List<String>> dayTasks : allWrappedTaskLines.values()) {
            for (List<String> taskLines : dayTasks) {
                if (taskLines.size() > maxNumberOfLinesPerTask) {
                    maxNumberOfLinesPerTask = taskLines.size();
                }
            }
        }
        
        return maxNumberOfLinesPerTask;
    }
    
    /**
     * Prints the tasks in a grid format for the week. The tasks across different days are aligned in the same row.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param maxNumberOfTasksInDay The maximum number of tasks in a day.
     * @param maxNumberOfLinesPerTask The maximum number of lines per task.
     */
    private static void printTasksInGrid(LocalDate startOfWeek, int maxNumberOfTasksInDay, 
            int maxNumberOfLinesPerTask) {
        for (int taskIndex = 0; taskIndex < maxNumberOfTasksInDay; taskIndex++) {
            for (int lineIndex = 0; lineIndex < maxNumberOfLinesPerTask; lineIndex++) {
                printTaskSubstringInRow(startOfWeek, taskIndex, lineIndex);
            }
        }
    }

    /**
     * Prints the substring of the task in the row.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param taskIndex The index of the task.
     * @param lineIndex The index of the line in the task.
     */
    private static void printTaskSubstringInRow(LocalDate startOfWeek, int taskIndex, int lineIndex) {
        for (int dayIndex = 0; dayIndex < numberOfDaysInWeek; dayIndex++) {
            LocalDate currentDate = startOfWeek.plusDays(dayIndex);

            List<List<String>> tasksWrappedLinesForDay = 
                    allWrappedTaskLines.getOrDefault(currentDate, Collections.emptyList());
            
            boolean hasEnoughTasks = taskIndex < tasksWrappedLinesForDay.size();
            boolean hasEnoughLines = lineIndex < tasksWrappedLinesForDay.get(taskIndex).size();
            if (hasEnoughTasks && hasEnoughLines) {
                String taskLine = tasksWrappedLinesForDay.get(taskIndex).get(lineIndex);
                System.out.printf(TASK_DISPLAY_FORMAT, taskLine);
            } else {
                System.out.print(EMPTY_TASK_DISPLAY_FORMAT);
            }
        }

        System.out.println(VERTICAL_DIVIDER);
    }

    /**
     * Stores the wrapped lines for the tasks in the week.
     * 
     * @param startOfWeek The date of the start of the week.
     * @param taskManager The task manager to get the tasks from.
     */
    private static void storeWrappedLines(LocalDate startOfWeek, TaskManager taskManager) {
        for (int dayIndex = 0; dayIndex < numberOfDaysInWeek; dayIndex++) {
            LocalDate currentDate = startOfWeek.plusDays(dayIndex);
            List<Task> dayTasks = taskManager.getTasksForDate(currentDate);
            List<List<String>> wrappedTasksForDay = new ArrayList<>();

            for (Task task : dayTasks) {
                String taskDescription = task.getName();
                String displayString = 
                        (dayTasks.indexOf(task) + 1) + "." + 
                        task.getDisplayFormat() +
                        taskDescription;
                List<String> wrappedLines = wrapText(displayString, SPACE_COUNT);
                wrappedTasksForDay.add(wrappedLines);
            }

            allWrappedTaskLines.put(currentDate, wrappedTasksForDay);
        }
    }

    /**
     * Wraps the text to fit the given maximum length.
     * 
     * @param text The text to wrap.
     * @param maxLengthToOccupy The maximum length to occupy.
     * @return The list of wrapped lines.
     */
    private static List<String> wrapText(String text, int maxLengthToOccupy) {
        List<String> lines = new ArrayList<>();
        while (text.length() > maxLengthToOccupy) {
            int breakPointIndex = text.lastIndexOf(' ', maxLengthToOccupy);
            if (breakPointIndex == -1) {
                breakPointIndex = maxLengthToOccupy;
            }
            lines.add(text.substring(0, breakPointIndex));
            text = text.substring(breakPointIndex).trim();
        }
        if (!text.isEmpty()) {
            lines.add(text);
        }
        return lines;
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
            String taskDescription = task.getName();
            String displayString = 
                    (taskIndex + 1) + "." + 
                    task.getDisplayFormat() +
                    taskDescription;
            System.out.printf(TASK_DISPLAY_FORMAT, displayString);
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
        for (int i = 0; i < numberOfDaysInWeek; i++) {
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
        System.out.println("| - 'priority, <day>, <taskIndex>, <priority>': Set priority level for a task.  |");
        System.out.println("| - 'month': Switch to month view.                                              |");
        System.out.println("| - 'week': Switch to week view.                                                |");
        System.out.println("| - 'quit': Exit the calendar application.                                      |");
        System.out.println(emptyLine);
        System.out.println(horizontalLine);
    }


}

