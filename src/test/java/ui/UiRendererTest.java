package ui;

import data.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ui.UiRenderer.printTaskForDay;

public class UiRendererTest {
    
    public static List<Task> tasks = List.of(new Task("task1"), new Task("task2"),
            new Task("task3"), new Task("task4"));
    public static List<Task> emptyTaskList = List.of();
    private static final int SPACE_COUNT = 15;
    private static final String VERTICAL_DIVIDER = "|";
    private static final String TASK_DISPLAY_FORMAT = VERTICAL_DIVIDER + "%-" + SPACE_COUNT + "." + SPACE_COUNT + "s";
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
    
}
