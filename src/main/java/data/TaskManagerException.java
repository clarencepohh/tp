package data;

import time.WeekView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TaskManagerException extends Exception {

    public static final String NOT_CURRENT_WEEK_MESSAGE =
            "The date must be within the current week. Please try again.";
    public static final String NOT_CURRENT_MONTH_MESSAGE =
            "The date must be within the current month. Please try again.";
    public static final String INVALID_DATE_FORMAT_MESSAGE =
            "Invalid date format. Please enter a valid date in the format: dd/MM/yyyy";
    public static final String INVALID_TIME_FORMAT_MESSAGE =
            "Invalid time format. Please enter a valid time in the format: HH:mm";
    public static final String NO_TASKS_MESSAGE =
            "There are no tasks on this date. Please try again.";
    public static final String START_DATE_AFTER_END_DATE_MESSAGE =
            "Start date must be before end date.";
    private static final String START_TIME_AFTER_END_TIME_MESSAGE =
            "Start time must be before end time.";

    /**
     * Constructor for TaskManagerException class.
     * Uses constructor from Exception superclass.
     *
     * @param errorMessage The error message to be printed to the console.
     */
    public TaskManagerException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Method that checks if the date is in the current week shown by the calendar.
     *
     * @param date The date to be checked.
     * @param weekView The current week view shown by the calendar.
     * @throws TaskManagerException if date is not in the current week.
     */
    public static void checkIfDateInCurrentWeek(LocalDate date, WeekView weekView) throws TaskManagerException {
        LocalDate startOfWeek = weekView.getStartOfWeek();
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        if (date.isBefore(startOfWeek) || date.isAfter(endOfWeek)) {
            throw new TaskManagerException(NOT_CURRENT_WEEK_MESSAGE);
        }
    }

    /**
     * Checks if the provided string is a valid date in the format "dd/MM/yyyy".
     * Throws a TaskManagerException if the date is not valid.
     *
     * @param date The date string to be checked.
     * @throws TaskManagerException If the date string is not a valid date in the format "dd/MM/yyyy".
     */
    public static void checkIfValidDate(String date) throws TaskManagerException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new TaskManagerException(INVALID_DATE_FORMAT_MESSAGE);
        }
    }

    /**
     * Checks if the provided string is a valid time in the format "HH:mm".
     * Throws a TaskManagerException if the time is not valid.
     *
     * @param time The time string to be checked.
     * @throws TaskManagerException If the time string is not a valid time in the format "HH:mm".
     */
    public static void checkIfValidTime(String time) throws TaskManagerException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new TaskManagerException(INVALID_TIME_FORMAT_MESSAGE);
        }
    }


    /**
     * Method that checks if the date is in the current month shown by the calendar.
     *
     * @param date The date to be checked.
     * @throws TaskManagerException if date is not in the current month.
     */
    public static void checkIfDateInCurrentMonth(LocalDate date) throws TaskManagerException {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        int providedMonth = date.getMonthValue();
        int providedYear = date.getYear();

        if (currentMonth != providedMonth || currentYear != providedYear) {
            throw new TaskManagerException(NOT_CURRENT_MONTH_MESSAGE);
        }
    }

    /**
     * Method that checks if a specified date has existing tasks.
     *
     * @param dayTasks The List containing the list of tasks for the specified date.
     * @throws TaskManagerException if the specified date has no tasks.
     */
    public static void checkIfDateHasTasks(List<Task> dayTasks) throws TaskManagerException {
        if (dayTasks.isEmpty()) {
            throw new TaskManagerException(NO_TASKS_MESSAGE);
        }
    }

    /**
     * Method that checks if start date is before end date, and throw TaskManagerException if not,
     * convert to LocalDate first
     *
     * @param startDate The start date to be checked.
     * @param endDate The end date to be checked.
     * @throws TaskManagerException if start date is after end date.
     */
    public static void checkIfStartDateBeforeEndDate(String startDate, String endDate) throws TaskManagerException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            if (start.isAfter(end)) {
                throw new TaskManagerException(START_DATE_AFTER_END_DATE_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            throw new TaskManagerException(INVALID_DATE_FORMAT_MESSAGE);
        }
    }

    /**
     * Method that checks if start time is before end time, and throw TaskManagerException if not,
     * convert to LocalTime first
     *
     * @param startTime The start time to be checked.
     * @param endTime The end time to be checked.
     * @throws TaskManagerException if start time is after end time.
     */
    public static void checkIfStartTimeBeforeEndTime(String startTime, String endTime) throws TaskManagerException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime start = LocalTime.parse(startTime, formatter);
            LocalTime end = LocalTime.parse(endTime, formatter);
            if (start.isAfter(end)) {
                throw new TaskManagerException(START_TIME_AFTER_END_TIME_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            throw new TaskManagerException(INVALID_TIME_FORMAT_MESSAGE);
        }
    }

    /**
     * Checks the validity of the provided date and time.
     * The date is checked for its format and whether it falls within the current week or month.
     * The time is checked for its format.
     *
     * @param weekView The current week view shown by the calendar.
     * @param inMonthView A boolean indicating whether the month is being viewed.
     * @param startDate The start date to be checked.
     * @param startTime The start time to be checked.
     * @throws TaskManagerException If the date or time is not valid, or does not fall within the current week or month.
     */
    public static void checkDateAndTimeValidity(WeekView weekView, boolean inMonthView, String startDate,
                                                String startTime) throws TaskManagerException {
        // Check if date is valid
        checkIfValidDate(startDate);

        if (inMonthView) {
            checkIfDateInCurrentMonth(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            checkIfDateInCurrentWeek(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")), weekView);
        }

        //Check if time is valid
        checkIfValidTime(startTime);
    }

}
