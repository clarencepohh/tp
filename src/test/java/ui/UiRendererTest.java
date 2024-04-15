package ui;

import data.Task;
import data.TaskManager;
import data.TaskType;
import data.exceptions.TaskManagerException;
import time.DateUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static data.TaskManager.addTask;
import static ui.UiRenderer.printWeekHeader;
import static ui.UiRenderer.printWeekBody;
import static data.TaskManager.deleteAllTasksOnDate;
import static time.DateUtils.getStartOfWeek;

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

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @AfterEach
    void resetTaskManager() {
        LocalDate date = LocalDate.now();
        deleteAllTasksOnDate(taskManager, date);
    }
    
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void printTaskForDay_noTasks_printsNoTasks() {
        printTaskForDay(emptyTaskList, 0);
        assertEquals(outContent.toString(), EMPTY_TASK_DISPLAY_FORMAT);
    }
    
    @Test
    void printWeekHeader_forWeekView_printsWeekViewHeading() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = DateUtils.getStartOfWeek(today);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printWeekHeader(startOfWeek, dateFormatter, false);

        String expectedOutput = 
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n" + 
                "|Sunday         |Monday         |Tuesday        |Wednesday      |" + 
                "Thursday       |Friday         |Saturday       |\n" + 
                "|14/04/2024     |15/04/2024     |16/04/2024     |17/04/2024     |" + 
                "18/04/2024     |19/04/2024     |20/04/2024     |\n" + 
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n";
                                
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void printWeekHeader_forMonthView_printsMonthViewHeading() {
        LocalDate startOfMonth = LocalDate.of(2023, 4, 1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printWeekHeader(startOfMonth, dateFormatter, true);

        String expectedOutput = 
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n" + 
                "|Sunday         |Monday         |Tuesday        |Wednesday      |" + 
                "Thursday       |Friday         |Saturday       |\n" + 
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void printWeekBody_noTasks_printsEmptyWeekBody() {
        LocalDate startOfWeek = DateUtils.getStartOfWeek(LocalDate.now());

        deleteAllTasksOnDate(taskManager, startOfWeek);

        outContent.reset();
        printWeekBody(startOfWeek, taskManager);

        String expectedOutput = 
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void printWeekBody_singleTaskPerDay_printsWeekBodyWithSingleTaskPerDay() throws TaskManagerException {
        LocalDate startOfWeek = DateUtils.getStartOfWeek(LocalDate.now());

        // Make sure the task manager is clear before adding new tasks
        deleteAllTasksOnDate(taskManager, startOfWeek);; // Assuming such a method exists
        addTask(startOfWeek, "Task 1", TaskType.TODO, new String[]{}, new String[]{});
        addTask(startOfWeek.plusDays(1), "Task 2", TaskType.TODO, new String[]{}, new String[]{});

        // Reset the output stream before printing
        outContent.reset();
        printWeekBody(startOfWeek, taskManager);

        String expectedOutput = 
                "|1.[T][O][L]    |1.[T][O][L]    |               |               |" + 
                "               |               |               |\n" +
                "|Task 1         |Task 2         |               |               |" + 
                "               |               |               |\n" +
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void printWeekBody_multipleTasksPerDay_printsWeekBodyWithMultipleTasksPerDay() throws TaskManagerException{
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = DateUtils.getStartOfWeek(today);

        addTask(startOfWeek, "Task 1", TaskType.TODO, new String[]{}, new String[]{});
        addTask(startOfWeek, "Task 2", TaskType.TODO, new String[]{}, new String[]{});
        addTask(startOfWeek.plusDays(1), "Task 3", TaskType.TODO, new String[]{}, new String[]{});
        addTask(startOfWeek.plusDays(1), "Task 4", TaskType.TODO, new String[]{}, new String[]{});

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printWeekBody(startOfWeek, taskManager);

        String expectedOutput = 
                "|1.[T][O][L]    |1.[T][O][L]    |               |               |" + 
                "               |               |               |\n" +
                "|Task 1         |Task 3         |               |               |" + 
                "               |               |               |\n" +
                "|2.[T][O][L]    |2.[T][O][L]    |               |               |" + 
                "               |               |               |\n" +
                "|Task 2         |Task 4         |               |               |" + 
                "               |               |               |\n" +
                "+---------------+---------------+---------------+---------------+" + 
                "---------------+---------------+---------------+\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
