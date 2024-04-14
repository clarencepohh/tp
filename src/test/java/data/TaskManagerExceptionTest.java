package data;

import data.exceptions.TaskManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import time.DateUtils;
import time.WeekView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static data.exceptions.TaskManagerException.checkIfDateHasTasks;
import static data.exceptions.TaskManagerException.checkIfDateInCurrentMonth;
import static data.exceptions.TaskManagerException.checkIfTimeInFormat;
import static data.exceptions.TaskManagerException.checkIfDateInCurrentWeek;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskManagerExceptionTest {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = DateUtils.getStartOfWeek(today);
    WeekView weekView = new WeekView(startOfWeek, dateFormatter);

    @Test
    public void checkIfDateInCurrentMonth_pastDateGiven_exceptionThrown () {
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
                checkIfDateInCurrentMonth(LocalDate.parse("1111-11-11")),
                "TaskManagerException was expected");

        Assertions.assertEquals("The date must be within the current month. " + "Please try again.",
                thrown.getMessage());
    }

    @Test
    public void checkIfTimeInFormat_invalidTimeGiven_exceptionThrown () {
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
                checkIfTimeInFormat("abcde"),
                "TaskManagerException was expected");

        Assertions.assertEquals("Invalid time format. Please use the format HHmm", thrown.getMessage());
    }

    @Test
    void checkIfTimeInFormat_invalidFormat_throwsException() {
        String invalidTime = "25:00";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidTime(invalidTime));
    }

    @Test
    void checkIfTimeInFormat_validFormat_noExceptionThrown() {
        String validTime = "23:59";
        try {
            TaskManagerException.checkIfValidTime(validTime);
        } catch (TaskManagerException e) {
            assert false;
        }
    }

    @Test
    void checkIfTimeInFormat_invalidMinute_throwsException() {
        String invalidTime = "23:60";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidTime(invalidTime));
    }

    @Test
    void checkIfValidDate_invalidFormat_throwsException() {
        String invalidDate = "25/13/2022";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidDate(invalidDate));
    }

    @Test
    void checkIfValidDate_validFormat_noExceptionThrown() {
        String validDate = "25/12/2022";
        try {
            TaskManagerException.checkIfValidDate(validDate);
        } catch (TaskManagerException e) {
            // This line should not be reached
            assert false;
        }
    }

    @Test
    void checkIfValidDate_invalidDay_throwsException() {
        String invalidDate = "32/12/2022";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidDate(invalidDate));
    }

    @Test
    void checkIfValidDate_invalidMonth_throwsException() {
        String invalidDate = "12/13/2022";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidDate(invalidDate));
    }

    @Test
    void checkIfValidTime_invalidFormat_throwsException() {
        String invalidTime = "25:00";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidTime(invalidTime));
    }

    @Test
    void checkIfValidTime_validFormat_noExceptionThrown() {
        String validTime = "23:59";
        try {
            TaskManagerException.checkIfValidTime(validTime);
        } catch (TaskManagerException e) {
            // This line should not be reached, if it is, the test will fail.
            assert false;
        }
    }

    @Test
    void checkIfValidTime_invalidMinute_throwsException() {
        String invalidTime = "23:60";
        assertThrows(TaskManagerException.class, () -> TaskManagerException.checkIfValidTime(invalidTime));
    }

    @Test
    public void checkIfDateInCurrentMonth_futureDateGiven_exceptionThrown () {
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
            checkIfDateInCurrentMonth(LocalDate.parse("2222-11-11")),
                "TaskManagerException was expected");

        Assertions.assertEquals("The date must be within the current month. " + "Please try again.",
                thrown.getMessage());
    }

    @Test
    void checkIfDateInCurrentMonth_sameMonthDifferentYear_throwsException() {
        LocalDate date = LocalDate.now().withYear(2023);
        assertThrows(TaskManagerException.class, () -> checkIfDateInCurrentMonth(date));
    }

    @Test
    void checkIfDateInCurrentMonth_sameYearDifferentMonth_throwsException() {
        LocalDate date = LocalDate.now().withMonth(1);
        assertThrows(TaskManagerException.class, () -> checkIfDateInCurrentMonth(date));
    }

    @Test
    void checkIfDateInCurrentMonth_sameMonthAndYear_noExceptionThrown() {
        LocalDate date = LocalDate.now();
        assertDoesNotThrow(() -> checkIfDateInCurrentMonth(date));
    }

    @Test
    public void checkIfDateInCurrentWeek_pastDateGiven_exceptionThrown () {
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
            checkIfDateInCurrentWeek(LocalDate.parse("1111-11-11"), weekView),
                "TaskManagerException was expected");

        Assertions.assertEquals("The date must be within the current week. Please try again.",
                thrown.getMessage());
    }

    @Test
    public void checkIfDateInCurrentWeek_futureDateGiven_exceptionThrown () {
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
                checkIfDateInCurrentWeek(LocalDate.parse("2222-11-11"), weekView),
                "TaskManagerException was expected");

        Assertions.assertEquals("The date must be within the current week. Please try again.",
                thrown.getMessage());
    }

    @Test
    public void checkIfDateHasTasks_noTasksOnDate_exceptionThrown () {
        List<Task> dayWithNoTasks = new ArrayList<>();
        TaskManagerException thrown = Assertions.assertThrows(TaskManagerException.class, () ->
                checkIfDateHasTasks(dayWithNoTasks));

        Assertions.assertEquals("There are no tasks on this date. Please try again.", thrown.getMessage());
    }
}
