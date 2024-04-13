package commandparser;

import data.exceptions.TaskManagerException;
import time.MonthView;
import time.WeekView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringParser {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateString the string representing the date.
     * @return the LocalDate object parsed from the string.
     * @throws TaskManagerException if the date string is not in the expected format.
     */
    public static LocalDate parseDate(String dateString) throws TaskManagerException {
        try {
            return LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new TaskManagerException("Invalid date format. Please use the format dd/MM/yyyy.");
        }
    }

    /**
     * Parses a task index string into an integer.
     *
     * @param indexString the string representing the task index.
     * @return the integer value of the task index.
     * @throws TaskManagerException if the task index string is not a valid integer.
     */
    public static int parseTaskIndex(String indexString) throws TaskManagerException {
        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new TaskManagerException("Invalid task index. Please enter a valid integer.");
        }
    }

    /**
     * Parses a task description string.
     *
     * @param descriptionString the string representing the task description.
     * @return the parsed task description string.
     */
    public static String parseTaskDescription(String descriptionString) {
        return descriptionString.trim();
    }

    /**
     * Parses a priority level string.
     *
     * @param priorityString the string representing the priority level.
     * @return the parsed priority level string.
     * @throws TaskManagerException if the priority level string is not valid.
     */
    public static String parsePriorityLevel(String priorityString) throws TaskManagerException {
        String trimmedPriority = priorityString.trim().toUpperCase();
        if (trimmedPriority.equals("H") || trimmedPriority.equals("M") || trimmedPriority.equals("L")) {
            return trimmedPriority;
        } else {
            throw new TaskManagerException("Invalid priority level. Please use 'high', 'medium', or 'low'.");
        }
    }

    /**
     * Validates the format of an "add" command.
     *
     * @param parts the array of command parts.
     * @throws TaskManagerException if the command format is invalid.
     */
    public static void validateAddCommand(String[] parts, WeekView weekView, MonthView monthView, boolean inMonthView)
            throws TaskManagerException {
        if (parts.length != 4) {
            throw new TaskManagerException("Invalid input format. Please provide input in the format: " +
                    "add, <day>, <taskType>, <taskDescription>");
        }
        int day = parseTaskIndex(parts[1]);
        if (inMonthView) {
            LocalDate startOfMonth = monthView.getStartOfMonth();
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
            if (day < 1 || day > endOfMonth.getDayOfMonth()) {
                throw new TaskManagerException("Invalid day for month view. Please enter a day between 1 and " +
                        endOfMonth.getDayOfMonth() + ".");
            }
        } else {
            LocalDate startOfWeek = weekView.getStartOfWeek();
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            LocalDate dateForDay = weekView.getDateForDay(day);
            if (dateForDay.isBefore(startOfWeek) || dateForDay.isAfter(endOfWeek)) {
                throw new TaskManagerException("Invalid day for week view. Please enter a day between " +
                        startOfWeek.getDayOfMonth() + " and " + endOfWeek.getDayOfMonth() + ".");
            }
        }
    }

    /**
     * Validates the format of an "update" command.
     *
     * @param parts the array of command parts.
     * @throws TaskManagerException if the command format is invalid.
     */
    public static void validateUpdateCommand(String[] parts) throws TaskManagerException {
        if (parts.length != 4) {
            throw new TaskManagerException("Invalid input format. Please provide input in the format: " +
                    "update, <day>, <taskIndex>, <newDescription>");
        }
    }

    /**
     * Validates the format of a "delete" command.
     *
     * @param parts the array of command parts.
     * @throws TaskManagerException if the command format is invalid.
     */
    public static void validateDeleteCommand(String[] parts) throws TaskManagerException {
        if (parts.length != 3) {
            throw new TaskManagerException("Invalid input format. Please provide input in the format: " +
                    "delete, <day>, <taskIndex>");
        }
    }

    /**
     * Validates the format of a command marking a task.
     *
     * @param parts the array of command parts.
     * @throws TaskManagerException if the command format is invalid.
     */
    public static void validateMarkCommand(String[] parts) throws TaskManagerException {
        if (parts.length != 3) {
            throw new TaskManagerException("Invalid input format. Please provide input in the format: " +
                    "mark, <day>, <taskIndex>");
        }
    }

    /**
     * Validates the format of a command setting priority.
     *
     * @param parts the array of command parts.
     * @throws TaskManagerException if the command format is invalid.
     */
    public static void validatePriorityCommand(String[] parts) throws TaskManagerException {
        if (parts.length != 4) {
            throw new TaskManagerException("Invalid input format. Please provide input in the format: " +
                    "priority, <day>, <taskIndex>, <priorityLevel>");
        }
    }
}
