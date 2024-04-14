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

import static data.exceptions.TaskManagerException.*;

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
                checkIfTimeInFormat("1111"),
                "TaskManagerException was expected");

        Assertions.assertEquals("Invalid time format. Please use the format HH:mm", thrown.getMessage());
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
