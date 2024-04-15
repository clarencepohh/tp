package data;

import data.exceptions.TaskManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import time.WeekView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;


import static data.TaskManager.addTask;
import static data.TaskManager.updateTask;
import static data.TaskManager.deleteAllTasksOnDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerTest {
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

    @Test
    void addTodo_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Todo";

        // Act
        Task testTask = new Todo(taskDescription);
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        addTask(date, taskDescription, testTaskType, dummyTestDates, dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
    }

    @Test
    void addTodo_invalidInput_throwsException() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Todo";

        // Act
        TaskType testInvalidTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testInvalidTaskType, dummyTestDates, dummyTestTimes));

        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }

    @Test
    void updateTodo_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial todo";
        String updatedTaskDescription = "Updated todo";
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        Scanner scanner = new Scanner(System.in);

        boolean inMonthView = false;
        WeekView weekView = new WeekView(LocalDate.now(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner,inMonthView, weekView);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void getTasksForDate_validDate_returnsTasks() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test todo task";
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);

        // Act
        List<Task> tasksForDate = taskManager.getTasksForDate(date);
        Task createdTask = tasksForDate.get(0);

        // Assert
        assertEquals(createdTask, tasksForDate.get(0));
    }

    @Test
    void addTodoFromFile_validInput_addsTasks() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        Map<LocalDate, List<Task>> tasksFromFile = new HashMap<>();
        String taskDescription = "Test todo task";
        Task testTodoTask = new Todo(taskDescription);
        tasksFromFile.put(date, List.of(testTodoTask));

        // Act
        taskManager.addTasksFromFile(tasksFromFile);

        // Assert
        assertEquals(testTodoTask.getName(), taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void addDeadline_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Deadline";
        String byDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String byTime = "1800";

        // Act
        Task testTask = new Deadline(taskDescription, byDate, byTime);
        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);
        String addedTaskByDate = addedTask.getByDate();
        String addedTaskByTime = addedTask.getByTime();

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
        assertEquals(testTask.getByDate(), addedTaskByDate);
        assertEquals(testTask.getByTime(), addedTaskByTime);
    }

    @Test
    void addDeadline_invalidInput_throwsException() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Deadline";
        String byDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String byTime = "1800";

        // Act
        TaskType testTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes));
        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }

    @Test
    void updateDeadlineDescriptionOnly_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Deadline";
        String updatedTaskDescription = "Updated Deadline";
        String byDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String byTime = "1800";
        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};
        String simulatedUserInput = "no";

        Scanner scanner = new Scanner(simulatedUserInput);

        boolean inMonthView = false;
        WeekView weekView = new WeekView(LocalDate.now(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner,inMonthView, weekView);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void updateDeadlineDescriptionAndByDateTime_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Deadline";
        String updatedTaskDescription = "Updated Deadline";
        String byDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String byTime = "1800";

        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};
        String simulatedUserInput = "yes\n" +
                LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 1500\n";
        String updatedByDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String updatedByTime = "1500";

        Scanner scanner = new Scanner(simulatedUserInput);

        boolean inMonthView = false;

        WeekView weekView = new WeekView(LocalDate.now(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner, inMonthView, weekView);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(LocalDate.parse(updatedByDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getName());
        assertEquals(updatedByTime, taskManager.getTasksForDate(LocalDate.parse(updatedByDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getByTime());
        assertEquals(updatedByDate, taskManager.getTasksForDate(LocalDate.parse(updatedByDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getByDate());
    }

    @Test
    void addEvent_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Event";
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = "1800";
        String endTime = "2000";

        // Act
        Task testTask = new Event(taskDescription, startDate, endDate, startTime, endTime);
        TaskType testTaskType = TaskType.EVENT;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);
        String addedTaskStartDate = addedTask.getStartDate();
        String addedTaskEndDate = addedTask.getEndDate();
        String addedTaskStartTime = addedTask.getStartTime();
        String addedTaskEndTime = addedTask.getEndTime();

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
        assertEquals(testTask.getStartDate(), addedTaskStartDate);
        assertEquals(testTask.getEndDate(), addedTaskEndDate);
        assertEquals(testTask.getStartTime(), addedTaskStartTime);
        assertEquals(testTask.getEndTime(), addedTaskEndTime);
    }

    @Test
    void addEvent_invalidInput_throwsException() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Event";
        String startDate = "05/04/2024";
        String endDate = "06/04/2024";
        String startTime = "1800";
        String endTime = "2000";

        // Act
        TaskType testTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes));
        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }

    @Test
    void updateEventDescriptionOnly_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Event";
        String updatedTaskDescription = "Updated Event";
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = "1800";
        String endTime = "2000";

        TaskType testTaskType = TaskType.EVENT;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};
        String simulatedUserInput = "no";

        Scanner scanner = new Scanner(simulatedUserInput);

        boolean inMonthView = false;

        WeekView weekView = new WeekView(LocalDate.now(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner, inMonthView, weekView);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void updateEventDescriptionDescriptionAndDateTime_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Event";
        String updatedTaskDescription = "Updated Event";
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = "1800";
        String endTime = "2000";

        TaskType testTaskType = TaskType.EVENT;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};
        String simulatedUserInput = "yes\n" +
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
                LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " 1500" + " 1600 ";
        String updatedStartDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String updatedStartTime = "1500";
        String updatedEndDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String updatedEndTime = "1600";

        Scanner scanner = new Scanner(simulatedUserInput);

        boolean inMonthView = false;

        WeekView weekView = new WeekView(LocalDate.now(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner, inMonthView, weekView);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(LocalDate.parse(updatedStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getName());
        assertEquals(updatedStartDate, taskManager.getTasksForDate(LocalDate.parse(updatedStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getStartDate());
        assertEquals(updatedStartTime, taskManager.getTasksForDate(LocalDate.parse(updatedStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getStartTime());
        assertEquals(updatedEndDate, taskManager.getTasksForDate(LocalDate.parse(updatedStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getEndDate());
        assertEquals(updatedEndTime, taskManager.getTasksForDate(LocalDate.parse(updatedStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0).getEndTime());
    }

    @Test
    void getFreeTimeSlots_validInput_returnsCorrectSlots() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.of(2024, 4, 7);
        String taskDescription = "Test Event";
        String startDate = "07/04/2024";
        String endDate = "07/04/2024";
        String startTime = "1200";
        String endTime = "1300";
        TaskType testTaskType = TaskType.EVENT;
        String[] testDates = new String[]{startDate, endDate};
        String[] testTimes = new String[]{startTime, endTime};

        // Act
        addTask(date, taskDescription, testTaskType, testDates, testTimes);
        List<String> freeTimeSlots = taskManager.getFreeTimeSlots(TaskManager.getEventsForDate(date), date);

        // Assert
        assertEquals(2, freeTimeSlots.size());
        assertEquals("00:00 - 12:00", freeTimeSlots.get(0));
        assertEquals("13:00 - 23:59", freeTimeSlots.get(1));
    }

    @Test
    void getFreeTimeSlots_invalidInput_returnsFullTime() {
        // Arrange
        LocalDate date = LocalDate.of(2024, 4, 7);

        // Act
        List<String> freeTimeSlots = taskManager.getFreeTimeSlots(new ArrayList<>(), date);

        // Assert
        assertEquals(freeTimeSlots.get(0), "00:00 - 23:59");
    }

    @Test
    void getEventsForDate_validDate_returnsEvents() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Event";
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = "1200";
        String endTime = "1300";
        TaskType testTaskType = TaskType.EVENT;
        String[] testDates = new String[]{startDate, endDate};
        String[] testTimes = new String[]{startTime, endTime};

        // Act
        addTask(date, taskDescription, testTaskType, testDates, testTimes);
        List<Task> eventsForDate = TaskManager.getEventsForDate(date);

        // Assert
        assertEquals(1, eventsForDate.size());
        assertEquals(taskDescription, eventsForDate.get(0).getName());
        assertEquals(startDate, eventsForDate.get(0).getStartDate());
        assertEquals(endDate, eventsForDate.get(0).getEndDate());
        assertEquals(startTime, eventsForDate.get(0).getStartTime());
        assertEquals(endTime, eventsForDate.get(0).getEndTime());
    }

    @Test
    void getEventsForDate_invalidDate_returnsNoEvents() {
        // Arrange
        LocalDate date = LocalDate.now(); // No events added for this date

        // Act
        List<Task> eventsForDate = TaskManager.getEventsForDate(date);

        // Assert
        assertTrue(eventsForDate.isEmpty());
    }

    @Test
    void markTaskAsCompleted_validIndex_marksTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        addTask(date, "Complete project report", TaskType.TODO, 
                new String[]{null}, new String[]{null});

        // Act
        taskManager.markTaskAsCompleted(date, 0);
        Task completedTask = taskManager.getTasksForDate(date).get(0);

        // Assert
        assertTrue(completedTask.isCompleted(), "Task should be marked as completed.");
    }

    @Test
    void markTaskAsCompleted_invalidIndex_throwsException() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        addTask(date, "Write unit test", TaskType.TODO, 
                new String[]{null}, new String[]{null});

        // Act & Assert
        IndexOutOfBoundsException exceptionThrown = assertThrows(
                IndexOutOfBoundsException.class, 
                () -> taskManager.markTaskAsCompleted(date, 1), 
                "Should throw IndexOutOfBoundsException");

        assertEquals(
                "Task index is out of bounds.", 
                exceptionThrown.getMessage(), 
                "Exception message should match expected.");
    }

    @Test
    void markTaskAsNotCompleted_validIndex_marksTaskNotCompleted() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        addTask(date, "Complete project report", TaskType.TODO, 
                new String[]{null}, new String[]{null});
        taskManager.markTaskAsCompleted(date, 0); 

        // Act
        taskManager.markTaskAsNotCompleted(date, 0);
        Task unmarkedTask = taskManager.getTasksForDate(date).get(0);

        // Assert
        assertFalse(unmarkedTask.isCompleted(), "Task should be marked as not completed.");
    }

    @Test
    void markTaskAsNotCompleted_invalidIndex_throwsException() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        addTask(date, "Write unit test", TaskType.TODO, 
                new String[]{null}, new String[]{null});

        // Act & Assert
        IndexOutOfBoundsException exceptionThrown = assertThrows(
                IndexOutOfBoundsException.class, 
                () -> taskManager.markTaskAsNotCompleted(date, 1), 
                "Should throw IndexOutOfBoundsException");

        assertEquals(
                "Task index is out of bounds.", 
                exceptionThrown.getMessage(), 
                "Exception message should match expected.");
    }
    
    @Test
    void getDayTasks_withMultipleTasks_returnsAllTasks() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        addTask(date, "Task 1", TaskType.TODO, new String[]{null}, new String[]{null});
        addTask(date, "Task 2", TaskType.TODO, new String[]{null}, new String[]{null});

        // Act
        List<Task> tasksForDay = TaskManager.getDayTasks(date);

        // Assert
        assertEquals(2, tasksForDay.size(), "Should return all tasks for the day.");
    }


}
