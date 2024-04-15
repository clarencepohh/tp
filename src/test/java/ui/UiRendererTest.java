package ui;

import data.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ui.UiRenderer.printTaskForDay;

public class UiRendererTest {
    
    public static List<Task> tasks = List.of(new Task("task1"), new Task("task2"),
            new Task("task3"), new Task("task4"));
    public static List<Task> emptyTaskList = List.of();
    private static final int SPACE_COUNT = 15;
    private static final String VERTICAL_DIVIDER = "|";
    private static final String EMPTY_TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + " ".repeat(SPACE_COUNT);
    
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void printTaskForDay_noTasks_printsNoTasks() {
        printTaskForDay(emptyTaskList, 0);
        assertEquals(outContent.toString(), EMPTY_TASK_DISPLAY_FORMAT);
    }
    
    @Test
    void printWeekHeader_forWeekView_printsWeekViewHeading() {
        LocalDate startOfWeek = LocalDate.of(2024, 4, 14);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        UiRenderer.printWeekHeader(startOfWeek, dateFormatter, false);

        String expectedOutput = 
                "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+\n" + 
                "|Sunday         |Monday         |Tuesday        |Wednesday      |Thursday       |Friday         |Saturday       |\n" + 
                "|14/04/2024     |15/04/2024     |16/04/2024     |17/04/2024     |18/04/2024     |19/04/2024     |20/04/2024     |\n" + 
                "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+\n";
                                
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void printWeekHeader_forMonthView_printsMonthViewHeading() {
        LocalDate startOfMonth = LocalDate.of(2023, 4, 1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        UiRenderer.printWeekHeader(startOfMonth, dateFormatter, true);

        String expectedOutput = 
                "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+\n" + 
                "|Sunday         |Monday         |Tuesday        |Wednesday      |Thursday       |Friday         |Saturday       |\n" + 
                "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
