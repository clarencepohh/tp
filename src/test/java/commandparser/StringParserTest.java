package commandparser;

import data.exceptions.TaskManagerException;
import org.junit.jupiter.api.Test;
import time.MonthView;
import time.WeekView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StringParserTest {

    @Test
    void parseDateValidFormat() {
        String validDateString = "15/03/2023";
        LocalDate expectedDate = LocalDate.of(2023, 3, 15);
        assertDoesNotThrow(() -> {
            LocalDate parsedDate = StringParser.parseDate(validDateString);
            assertEquals(expectedDate, parsedDate);
        });
    }

    @Test
    void parseDateInvalidFormat() {
        String invalidDateString = "03-15-2023";
        assertThrows(TaskManagerException.class, () -> StringParser.parseDate(invalidDateString));
    }

    @Test
    void parseTaskIndexValidInteger() {
        String validIndexString = "5";
        int expectedIndex = 5;
        assertDoesNotThrow(() -> {
            int parsedIndex = StringParser.parseTaskIndex(validIndexString);
            assertEquals(expectedIndex, parsedIndex);
        });
    }

    @Test
    void parseTaskIndexInvalidInteger() {
        String invalidIndexString = "five";
        assertThrows(TaskManagerException.class, () -> StringParser.parseTaskIndex(invalidIndexString));
    }

    @Test
    void parseTaskDescriptionValidDescription() {
        String description = "  This is a task description.  ";
        String expectedDescription = "This is a task description.";
        assertEquals(expectedDescription, StringParser.parseTaskDescription(description));
    }

    @Test
    void parsePriorityLevelValidHigh() {
        String priority = " H ";
        String expectedPriority = "H";
        assertDoesNotThrow(() -> {
            String parsedPriority = StringParser.parsePriorityLevel(priority);
            assertEquals(expectedPriority, parsedPriority);
        });
    }

    @Test
    void parsePriorityLevelValidMedium() {
        String priority = "m";
        String expectedPriority = "M";
        assertDoesNotThrow(() -> {
            String parsedPriority = StringParser.parsePriorityLevel(priority);
            assertEquals(expectedPriority, parsedPriority);
        });
    }

    @Test
    void parsePriorityLevelValidLow() {
        String priority = "L";
        String expectedPriority = "L";
        assertDoesNotThrow(() -> {
            String parsedPriority = StringParser.parsePriorityLevel(priority);
            assertEquals(expectedPriority, parsedPriority);
        });
    }

    @Test
    void parsePriorityLevelInvalidPriority() {
        String invalidPriority = "X";
        assertThrows(TaskManagerException.class, () -> StringParser.parsePriorityLevel(invalidPriority));
    }

    @Test
    void validateAddCommandValidFormat() {
        // Ensure the day is within the range of the current week starting on Sunday.
        String[] validParts = {"add", "12", "taskType", "taskDescription"}; // Adjusted to Sunday
        LocalDate startOfWeek = LocalDate.of(2024, 4, 7); // Sunday of the week
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        WeekView weekView = new WeekView(startOfWeek, dateFormatter);
        MonthView monthView = new MonthView(startOfWeek, dateFormatter);
        boolean inMonthView = false;

        assertDoesNotThrow(() -> StringParser.validateAddCommand(validParts));
    }

    @Test
    void validateAddCommandInvalidFormat() {
        String[] invalidParts = {"add", "15/03/2023", "taskDescription"};
        LocalDate startOfWeek = LocalDate.of(2023, 3, 13);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        WeekView weekView = new WeekView(startOfWeek, dateFormatter);
        MonthView monthView = new MonthView(startOfWeek, dateFormatter);
        boolean inMonthView = false;
        assertThrows(TaskManagerException.class, () ->
                StringParser.validateAddCommand(invalidParts));
    }

    @Test
    void validateUpdateCommandValidFormat() {
        String[] validParts = {"update", "15/03/2023", "1", "newDescription"};
        assertDoesNotThrow(() -> StringParser.validateUpdateCommand(validParts));
    }

    @Test
    void validateUpdateCommandInvalidFormat() {
        String[] invalidParts = {"update", "15/03/2023", "newDescription"};
        assertThrows(TaskManagerException.class, () -> StringParser.validateUpdateCommand(invalidParts));
    }

    @Test
    void validateDeleteCommandValidFormat() {
        String[] validParts = {"delete", "15/03/2023", "1"};
        assertDoesNotThrow(() -> StringParser.validateDeleteCommand(validParts));
    }

    @Test
    void validateDeleteCommandInvalidFormat() {
        String[] invalidParts = {"delete", "15/03/2023"};
        assertThrows(TaskManagerException.class, () -> StringParser.validateDeleteCommand(invalidParts));
    }

    @Test
    void validateMarkCommandValidFormat() {
        String[] validParts = {"mark", "15/03/2023", "1"};
        assertDoesNotThrow(() -> StringParser.validateMarkCommand(validParts));
    }

    @Test
    void validateMarkCommandInvalidFormat() {
        String[] invalidParts = {"mark", "15/03/2023"};
        assertThrows(TaskManagerException.class, () -> StringParser.validateMarkCommand(invalidParts));
    }

    @Test
    void validatePriorityCommandValidFormat() {
        String[] validParts = {"priority", "15/03/2023", "1", "H"};
        assertDoesNotThrow(() -> StringParser.validatePriorityCommand(validParts));
    }

    @Test
    void validatePriorityCommandInvalidFormat() {
        String[] invalidParts = {"priority", "15/03/2023", "1"};
        assertThrows(TaskManagerException.class, () -> StringParser.validatePriorityCommand(invalidParts));
    }
}
